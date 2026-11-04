import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { CartProvider } from './context/CartContext';
import Navbar from './components/Navbar';
import PrivateRoute from './components/PrivateRoute';
import Home from './pages/Home';
import LoginPage from './pages/LoginPage';
import CartPage from './pages/CartPage';
import OrdersPage from './pages/OrdersPage';

function App() {
    return (
        <BrowserRouter>
            <AuthProvider>
                <CartProvider>
                    <Navbar />
                    <main style={{ padding: '2rem', maxWidth: '1200px', margin: '0 auto' }}>
                        <Routes>
                            <Route path="/" element={<Home />} />
                            <Route path="/login" element={<LoginPage />} />
                            <Route path="/carrinho" element={<CartPage />} />
                            <Route path="/pedidos" element={
                                <PrivateRoute>
                                    <OrdersPage />
                                </PrivateRoute>
                            } />
                        </Routes>
                    </main>
                </CartProvider>
            </AuthProvider>
        </BrowserRouter>
    );
}

export default App;
