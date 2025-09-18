import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import apiClient from '../../services/api.js';

export const fetchCart = createAsyncThunk('cart/fetch', async (_, { rejectWithValue }) => {
  try {
    const { data } = await apiClient.get('/cart');
    return data;
  } catch (error) {
    return rejectWithValue(error.response?.data || { error: 'Unable to load cart' });
  }
});

export const saveCart = createAsyncThunk('cart/save', async (items, { rejectWithValue }) => {
  try {
    const payload = { itemsJson: JSON.stringify(items) };
    const { data } = await apiClient.put('/cart', payload);
    return data;
  } catch (error) {
    return rejectWithValue(error.response?.data || { error: 'Unable to update cart' });
  }
});

export const clearCart = createAsyncThunk('cart/clear', async (_, { rejectWithValue }) => {
  try {
    await apiClient.delete('/cart');
    return [];
  } catch (error) {
    return rejectWithValue(error.response?.data || { error: 'Unable to clear cart' });
  }
});

const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    items: [],
    status: 'idle',
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCart.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchCart.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.items = action.payload?.itemsJson ? JSON.parse(action.payload.itemsJson) : [];
      })
      .addCase(fetchCart.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload?.error || 'Unable to load cart';
      })
      .addCase(saveCart.fulfilled, (state, action) => {
        state.items = action.payload?.itemsJson ? JSON.parse(action.payload.itemsJson) : [];
      })
      .addCase(clearCart.fulfilled, (state) => {
        state.items = [];
      });
  },
});

export default cartSlice.reducer;
