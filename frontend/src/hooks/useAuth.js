import { useSelector } from 'react-redux';

const useAuth = () => {
  const { token, user } = useSelector((state) => state.auth);
  return {
    isAuthenticated: Boolean(token),
    user,
  };
};

export default useAuth;
