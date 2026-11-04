import { NavLink } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
    const { totalItems } = useCart();
    const { autenticado, user, logout } = useAuth();

    const linkStyle = ({ isActive }) => ({
        color: 'white',
        textDecoration: 'none',
        fontWeight: isActive ? 'bold' : 'normal',
        borderBottom: isActive ? '2px solid white' : 'none'
    });

    return (
        <nav style={{
            background: '#15803d', color: 'white',
            padding: '1rem 2rem', display: 'flex',
            justifyContent: 'space-between', alignItems: 'center'
        }}>
            <NavLink to="/" style={linkStyle}>
                <strong style={{ fontSize: '1.25rem' }}>🥬 Feira Viva</strong>
            </NavLink>
            <div style={{ display: 'flex', gap: '1.5rem', alignItems: 'center' }}>
                <NavLink to="/" style={linkStyle}>Catálogo</NavLink>
                <NavLink to="/carrinho" style={linkStyle}>
                    Carrinho ({totalItems})
                </NavLink>
                {autenticado ? (
                    <span style={{ display: 'flex', alignItems: 'center' }}>
                        Olá, {user.email}
                        <button onClick={logout} style={{
                            marginLeft: '0.8rem', background: 'transparent',
                            color: 'white', border: '1px solid white',
                            borderRadius: '4px', padding: '0.2rem 0.5rem', cursor: 'pointer'
                        }}>Sair</button>
                    </span>
                ) : (
                    <NavLink to="/login" style={linkStyle}>Entrar</NavLink>
                )}
                <NavLink to="/pedidos" style={linkStyle}>Pedidos</NavLink>
            </div>
        </nav>
    );
}
