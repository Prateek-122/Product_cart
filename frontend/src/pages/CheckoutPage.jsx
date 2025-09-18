import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import apiClient from '../services/api.js';
import { clearCart } from '../features/cart/cartSlice.js';

const CheckoutPage = () => {
  const { items } = useSelector((state) => state.cart);
  const dispatch = useDispatch();
  const [status, setStatus] = useState('idle');
  const [paymentMethod, setPaymentMethod] = useState('CARD');

  const total = items.reduce((sum, item) => sum + item.quantity * 100, 0);

  const handleCheckout = async () => {
    if (items.length === 0) {
      return;
    }
    try {
      setStatus('processing');
      const orderPayload = {
        itemsJson: JSON.stringify(items),
        total,
      };
      const { data: order } = await apiClient.post('/orders', orderPayload);
      await apiClient.post('/payments', {
        orderId: order.id,
        amount: total,
        method: paymentMethod,
      });
      dispatch(clearCart());
      setStatus('success');
    } catch (error) {
      console.error(error);
      setStatus('error');
    }
  };

  return (
    <div className="max-w-3xl mx-auto px-4 py-10 space-y-6">
      <h1 className="text-3xl font-semibold">Checkout</h1>
      <div className="bg-white shadow rounded-xl p-6 space-y-4">
        <h2 className="text-xl font-semibold">Order Summary</h2>
        <ul className="space-y-2">
          {items.map((item) => (
            <li key={item.productId} className="flex justify-between text-sm text-slate-600">
              <span>{item.title}</span>
              <span>Qty: {item.quantity}</span>
            </li>
          ))}
        </ul>
        <div className="flex items-center justify-between font-semibold text-lg">
          <span>Total</span>
          <span>₹{total.toFixed(2)}</span>
        </div>
        <div className="space-y-2">
          <label className="text-sm font-medium text-slate-600">Payment Method</label>
          <select
            className="border border-slate-300 rounded-md px-4 py-2"
            value={paymentMethod}
            onChange={(e) => setPaymentMethod(e.target.value)}
          >
            <option value="CARD">Card</option>
            <option value="UPI">UPI</option>
            <option value="NET_BANKING">Net Banking</option>
            <option value="COD">Cash on Delivery</option>
          </select>
        </div>
        <button
          onClick={handleCheckout}
          disabled={status === 'processing'}
          className="bg-brand text-white px-4 py-2 rounded-md font-semibold disabled:opacity-50"
        >
          {status === 'processing' ? 'Processing...' : 'Pay & Place Order'}
        </button>
        {status === 'success' && <p className="text-green-600 text-sm">Order placed successfully!</p>}
        {status === 'error' && <p className="text-red-600 text-sm">Something went wrong. Try again.</p>}
      </div>
    </div>
  );
};

export default CheckoutPage;
