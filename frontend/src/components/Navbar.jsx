export default function Navbar() {
    return (
        <nav style={{
            background: '#15803d',
            color: 'white',
            padding: '1rem 2rem',
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center'
        }}>
            <strong style={{ fontSize: '1.25rem' }}>🥬 Feira Viva</strong>
            <div style={{ display: 'flex', gap: '1.5rem' }}>
                <a href="/" style={{ color: 'white', textDecoration: 'none' }}>Catálogo</a>
                <a href="/carrinho" style={{ color: 'white', textDecoration: 'none' }}>Carrinho</a>
                <a href="/login" style={{ color: 'white', textDecoration: 'none' }}>Entrar</a>
            </div>
        </nav>
    );
}
