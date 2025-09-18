const Footer = () => (
  <footer className="bg-slate-900 text-white py-6 mt-12">
    <div className="max-w-7xl mx-auto px-4 flex flex-col md:flex-row items-center justify-between gap-4">
      <p className="text-sm">&copy; {new Date().getFullYear()} ProductCart. All rights reserved.</p>
      <div className="flex gap-4 text-sm">
        <a href="#" className="hover:text-brand">Privacy</a>
        <a href="#" className="hover:text-brand">Terms</a>
        <a href="#" className="hover:text-brand">Support</a>
      </div>
    </div>
  </footer>
);

export default Footer;
