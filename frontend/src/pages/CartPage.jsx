import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCart, saveCart, clearCart } from '../features/cart/cartSlice.js';
import { Link } from 'react-router-dom';

const CartPage = () => {
  const dispatch = useDispatch();
  const { items } = useSelector((state) => state.cart);

  useEffect(() => {
    dispatch(fetchCart());
  }, [dispatch]);

  const updateQuantity = (productId, delta) => {
    const updated = items.map((item) =>
      item.productId === productId ? { ...item, quantity: Math.max(1, item.quantity + delta) } : item
    );
    dispatch(saveCart(updated));
  };

  const removeItem = (productId) => {
    const updated = items.filter((item) => item.productId !== productId);
    dispatch(saveCart(updated));
  };

  const checkout = () => {
    // would call backend orders API
    alert('Checkout flow would be initiated with backend orders and payments API.');
  };

  if (items.length === 0) {
    return (
      <div className="max-w-4xl mx-auto px-4 py-10 text-center space-y-4">
        <h1 className="text-2xl font-semibold">Your cart is empty</h1>
        <Link to="/products" className="text-brand font-semibold">Browse products</Link>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-10 space-y-6">
      <h1 className="text-3xl font-semibold">Shopping Cart</h1>
      <div className="bg-white shadow rounded-xl divide-y">
        {items.map((item) => (
          <div key={item.productId} className="p-4 flex items-center justify-between">
            <div>
              <p className="font-semibold text-slate-800">{item.title}</p>
              <p className="text-sm text-slate-500">Qty: {item.quantity}</p>
            </div>
            <div className="flex items-center gap-2">
              <button className="px-2 py-1 border rounded" onClick={() => updateQuantity(item.productId, -1)}>
                -
              </button>
              <button className="px-2 py-1 border rounded" onClick={() => updateQuantity(item.productId, 1)}>
                +
              </button>
              <button className="text-sm text-red-500" onClick={() => removeItem(item.productId)}>
                Remove
              </button>
            </div>
          </div>
        ))}
      </div>
      <div className="flex gap-4">
        <button onClick={() => dispatch(clearCart())} className="border border-slate-300 px-4 py-2 rounded-md">
          Clear Cart
        </button>
        <button onClick={checkout} className="bg-brand text-white px-4 py-2 rounded-md font-semibold">
          Proceed to Checkout
        </button>
      </div>
    </div>
  );
};

export default CartPage;
