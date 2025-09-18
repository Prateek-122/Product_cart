import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../features/auth/authSlice.js';

const Navbar = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { user } = useSelector((state) => state.auth);

  const handleLogout = () => {
    dispatch(logout());
    navigate('/login');
  };

  return (
    <header className="bg-white shadow-sm">
      <div className="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
        <Link to="/" className="text-2xl font-bold text-brand">
          ProductCart
        </Link>
        <nav className="flex items-center gap-4">
          <NavLink to="/products" className={({ isActive }) => isActive ? 'text-brand font-semibold' : 'text-slate-600 hover:text-brand'}>
            Products
          </NavLink>
          <NavLink to="/cart" className={({ isActive }) => isActive ? 'text-brand font-semibold' : 'text-slate-600 hover:text-brand'}>
            Cart
          </NavLink>
          {user ? (
            <div className="flex items-center gap-2">
              <span className="text-sm text-slate-600">Hi, {user.name || user.email}</span>
              <button onClick={handleLogout} className="text-sm text-brand hover:text-brand-dark">
                Logout
              </button>
            </div>
          ) : (
            <NavLink to="/login" className={({ isActive }) => isActive ? 'text-brand font-semibold' : 'text-slate-600 hover:text-brand'}>
              Login
            </NavLink>
          )}
        </nav>
      </div>
    </header>
  );
};

export default Navbar;
