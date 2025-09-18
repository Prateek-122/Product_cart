import { Link } from 'react-router-dom';

const ProductCard = ({ product }) => (
  <div className="bg-white rounded-lg shadow hover:shadow-lg transition overflow-hidden">
    <div className="p-4 space-y-3">
      <div className="h-40 bg-slate-100 flex items-center justify-center text-slate-400 text-sm">
        Image
      </div>
      <h3 className="text-lg font-semibold text-slate-800">{product.title}</h3>
      <p className="text-sm text-slate-500 max-h-16 overflow-hidden">{product.description}</p>
      <div className="flex items-center justify-between">
        <span className="text-brand font-semibold">SKU: {product.sku}</span>
        <Link to={`/products/${product.id}`} className="text-sm text-brand hover:text-brand-dark">
          View
        </Link>
      </div>
    </div>
  </div>
);

export default ProductCard;
