import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import apiClient from '../../services/api.js';

export const fetchProducts = createAsyncThunk('products/fetchAll', async (_, { rejectWithValue }) => {
  try {
    const { data } = await apiClient.get('/products');
    return data;
  } catch (error) {
    return rejectWithValue(error.response?.data || { error: 'Failed to load products' });
  }
});

export const fetchProductById = createAsyncThunk('products/fetchById', async (id, { rejectWithValue }) => {
  try {
    const { data } = await apiClient.get(`/products/${id}`);
    return data;
  } catch (error) {
    return rejectWithValue(error.response?.data || { error: 'Failed to load product' });
  }
});

export const searchProducts = createAsyncThunk('products/search', async (params, { rejectWithValue }) => {
  try {
    const { keyword, categoryId } = params || {};
    const searchParams = new URLSearchParams();
    if (keyword) searchParams.append('keyword', keyword);
    if (categoryId) searchParams.append('categoryId', categoryId);
    const { data } = await apiClient.get(`/products/search?${searchParams.toString()}`);
    return data;
  } catch (error) {
    return rejectWithValue(error.response?.data || { error: 'Search failed' });
  }
});

const productSlice = createSlice({
  name: 'products',
  initialState: {
    items: [],
    selected: null,
    status: 'idle',
    error: null,
    filters: {
      keyword: '',
      categoryId: '',
    },
  },
  reducers: {
    setFilters: (state, action) => {
      state.filters = { ...state.filters, ...action.payload };
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.items = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload?.error || 'Failed to load products';
      })
      .addCase(fetchProductById.fulfilled, (state, action) => {
        state.selected = action.payload;
      })
      .addCase(searchProducts.fulfilled, (state, action) => {
        state.items = action.payload;
      });
  },
});

export const { setFilters } = productSlice.actions;
export default productSlice.reducer;
