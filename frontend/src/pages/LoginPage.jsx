import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function LoginPage() {
    const { login } = useAuth();
    const navigate = useNavigate();

    function entrarDemo() {
        // AULA 19: será substituído pelo POST /auth/login real
        login('token-demo', 1, 'maria@email.com');
        navigate('/');
    }

    return (
        <>
            <h1>Entrar</h1>
            <p style={{ color: '#a1a1aa' }}>
                A autenticação real (JWT) será conectada na Aula 19.
                Por enquanto, use o botão de demonstração.
            </p>
            <button onClick={entrarDemo} style={{
                background: '#15803d', color: 'white',
                border: 'none', padding: '0.6rem 1.2rem',
                borderRadius: '6px', cursor: 'pointer'
            }}>
                Entrar (demo)
            </button>
        </>
    );
}

