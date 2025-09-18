import { Navigate, Outlet } from 'react-router-dom';
import { useSelector } from 'react-redux';

const ProtectedRoute = ({ roles }) => {
  const { token, user } = useSelector((state) => state.auth);
  if (!token) {
    return <Navigate to="/login" replace />;
  }
  if (roles && roles.length > 0) {
    const hasRole = user?.roles?.some((role) => roles.includes(role)) || user?.roles?.some((role) => roles.includes(role.replace('ROLE_', '')));
    if (!hasRole) {
      return <Navigate to="/" replace />;
    }
  }
  return <Outlet />;
};

export default ProtectedRoute;
