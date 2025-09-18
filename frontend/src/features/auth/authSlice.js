import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import apiClient from '../../services/api.js';

const storedToken = localStorage.getItem('token');
const storedUser = localStorage.getItem('user');

export const signup = createAsyncThunk('auth/signup', async (payload, { rejectWithValue }) => {
  try {
    const { data } = await apiClient.post('/auth/signup', payload);
    return data;
  } catch (error) {
    return rejectWithValue(error.response?.data || { error: 'Signup failed' });
  }
});

export const login = createAsyncThunk('auth/login', async (payload, { rejectWithValue }) => {
  try {
    const { data } = await apiClient.post('/auth/login', payload);
    return data;
  } catch (error) {
    return rejectWithValue(error.response?.data || { error: 'Login failed' });
  }
});

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    user: storedUser ? JSON.parse(storedUser) : null,
    token: storedToken || null,
    status: 'idle',
    error: null,
  },
  reducers: {
    logout: (state) => {
      state.user = null;
      state.token = null;
      localStorage.removeItem('token');
      localStorage.removeItem('user');
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(signup.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(signup.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.user = { email: action.payload.email, roles: Array.from(action.payload.roles || []) };
        state.token = action.payload.token;
        localStorage.setItem('token', action.payload.token);
        localStorage.setItem('user', JSON.stringify(state.user));
      })
      .addCase(signup.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload?.error || 'Signup failed';
      })
      .addCase(login.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(login.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.user = { email: action.payload.email, roles: Array.from(action.payload.roles || []) };
        state.token = action.payload.token;
        localStorage.setItem('token', action.payload.token);
        localStorage.setItem('user', JSON.stringify(state.user));
      })
      .addCase(login.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload?.error || 'Login failed';
      });
  },
});

export const { logout } = authSlice.actions;
export default authSlice.reducer;
