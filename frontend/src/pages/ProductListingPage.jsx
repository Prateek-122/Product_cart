import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import ProductCard from '../components/ProductCard.jsx';
import { fetchProducts, searchProducts, setFilters } from '../features/products/productSlice.js';
import apiClient from '../services/api.js';

const ProductListingPage = () => {
  const dispatch = useDispatch();
  const { items, filters } = useSelector((state) => state.products);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    dispatch(fetchProducts());
    apiClient.get('/categories').then(({ data }) => setCategories(data));
  }, [dispatch]);

  const handleSearch = (event) => {
    event.preventDefault();
    dispatch(searchProducts(filters));
  };

  return (
    <div className="max-w-7xl mx-auto px-4 py-10">
      <div className="bg-white shadow rounded-xl p-6 mb-8">
        <form onSubmit={handleSearch} className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <input
            type="text"
            value={filters.keyword}
            onChange={(e) => dispatch(setFilters({ keyword: e.target.value }))}
            placeholder="Search products"
            className="border border-slate-200 rounded-md px-4 py-2 focus:outline-none focus:ring-2 focus:ring-brand"
          />
          <select
            value={filters.categoryId}
            onChange={(e) => dispatch(setFilters({ categoryId: e.target.value }))}
            className="border border-slate-200 rounded-md px-4 py-2 focus:outline-none focus:ring-2 focus:ring-brand"
          >
            <option value="">All categories</option>
            {categories.map((category) => (
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
          <button type="submit" className="bg-brand text-white rounded-md px-4 py-2 font-semibold">
            Search
          </button>
        </form>
      </div>

      <div className="grid gap-6 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3">
        {items.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
    </div>
  );
};

export default ProductListingPage;
