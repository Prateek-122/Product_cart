import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { login, signup } from '../features/auth/authSlice.js';
import { Navigate } from 'react-router-dom';

const AuthPage = () => {
  const dispatch = useDispatch();
  const { token, status, error } = useSelector((state) => state.auth);
  const [isSignup, setIsSignup] = useState(false);
  const [form, setForm] = useState({ name: '', email: '', password: '', phone: '' });

  if (token) {
    return <Navigate to="/" replace />;
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    if (isSignup) {
      dispatch(signup({ name: form.name, email: form.email, password: form.password, phone: form.phone }));
    } else {
      dispatch(login({ email: form.email, password: form.password }));
    }
  };

  return (
    <div className="max-w-md mx-auto px-4 py-12">
      <div className="bg-white shadow-lg rounded-xl p-8 space-y-6">
        <h1 className="text-2xl font-semibold text-center">{isSignup ? 'Create account' : 'Welcome back'}</h1>
        <form onSubmit={handleSubmit} className="space-y-4">
          {isSignup && (
            <input
              type="text"
              placeholder="Full name"
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
              className="w-full border border-slate-300 rounded-md px-4 py-2"
              required
            />
          )}
          {isSignup && (
            <input
              type="tel"
              placeholder="Phone"
              value={form.phone}
              onChange={(e) => setForm({ ...form, phone: e.target.value })}
              className="w-full border border-slate-300 rounded-md px-4 py-2"
              required
            />
          )}
          <input
            type="email"
            placeholder="Email"
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
            className="w-full border border-slate-300 rounded-md px-4 py-2"
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })}
            className="w-full border border-slate-300 rounded-md px-4 py-2"
            required
          />
          <button
            type="submit"
            className="w-full bg-brand text-white rounded-md py-2 font-semibold"
            disabled={status === 'loading'}
          >
            {status === 'loading' ? 'Please wait...' : isSignup ? 'Sign up' : 'Login'}
          </button>
        </form>
        {error && <p className="text-sm text-red-500 text-center">{error}</p>}
        <p className="text-sm text-center text-slate-600">
          {isSignup ? 'Already have an account?' : "Don't have an account?"}{' '}
          <button className="text-brand" onClick={() => setIsSignup(!isSignup)}>
            {isSignup ? 'Login' : 'Sign up'}
          </button>
        </p>
      </div>
    </div>
  );
};

export default AuthPage;
