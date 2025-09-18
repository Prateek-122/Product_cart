import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchProducts } from '../features/products/productSlice.js';
import ProductCard from '../components/ProductCard.jsx';
import { Link } from 'react-router-dom';

const HomePage = () => {
  const dispatch = useDispatch();
  const { items } = useSelector((state) => state.products);

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-10 space-y-16">
      <section className="bg-gradient-to-r from-brand to-brand-dark text-white rounded-2xl p-10 flex flex-col md:flex-row items-center gap-6">
        <div className="flex-1 space-y-4">
          <h1 className="text-4xl font-bold">Discover products tailored for you.</h1>
          <p className="text-lg text-blue-100 max-w-xl">Shop the latest electronics, fashion, and lifestyle essentials with lightning-fast delivery and exclusive promotions.</p>
          <div className="flex gap-4">
            <Link to="/products" className="bg-white text-brand px-5 py-2 rounded-md font-semibold">Start Shopping</Link>
            <Link to="/login" className="border border-white px-5 py-2 rounded-md font-semibold">Join Now</Link>
          </div>
        </div>
        <div className="flex-1">
          <div className="bg-white/20 rounded-xl p-6 backdrop-blur-sm shadow-lg">
            <p className="text-sm uppercase tracking-widest text-blue-100">Trusted by global sellers</p>
            <p className="text-3xl font-semibold">50K+ products curated weekly</p>
          </div>
        </div>
      </section>

      <section className="space-y-4">
        <div className="flex items-center justify-between">
          <h2 className="text-2xl font-semibold">Featured Products</h2>
          <Link to="/products" className="text-brand hover:text-brand-dark text-sm font-semibold">View all</Link>
        </div>
        <div className="grid gap-6 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3">
          {items.slice(0, 6).map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>
      </section>
    </div>
  );
};

export default HomePage;
