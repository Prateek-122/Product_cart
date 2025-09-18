import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { fetchProductById } from '../features/products/productSlice.js';
import { saveCart } from '../features/cart/cartSlice.js';
import useAuth from '../hooks/useAuth.js';

const ProductDetailPage = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { selected } = useSelector((state) => state.products);
  const { items } = useSelector((state) => state.cart);
  const { isAuthenticated } = useAuth();

  useEffect(() => {
    dispatch(fetchProductById(id));
  }, [dispatch, id]);

  const addToCart = () => {
    if (!isAuthenticated) {
      alert('Please login to add items to your cart.');
      return;
    }
    const updated = [...items];
    const existing = updated.find((item) => item.productId === selected.id);
    if (existing) {
      existing.quantity += 1;
    } else {
      updated.push({ productId: selected.id, title: selected.title, quantity: 1 });
    }
    dispatch(saveCart(updated));
  };

  if (!selected) {
    return <div className="max-w-7xl mx-auto px-4 py-10">Loading...</div>;
  }

  return (
    <div className="max-w-5xl mx-auto px-4 py-10 grid grid-cols-1 md:grid-cols-2 gap-10">
      <div className="bg-white shadow rounded-xl h-80 flex items-center justify-center text-slate-400">
        Product Image
      </div>
      <div className="space-y-4">
        <h1 className="text-3xl font-semibold text-slate-900">{selected.title}</h1>
        <p className="text-slate-600">{selected.description}</p>
        <p className="text-sm text-slate-500">SKU: {selected.sku}</p>
        <button onClick={addToCart} className="bg-brand text-white px-6 py-3 rounded-md font-semibold">
          Add to Cart
        </button>
      </div>
    </div>
  );
};

export default ProductDetailPage;
