import { useCart } from '../context/CartContext';

export default function ProdutoCard({ produto }) {
    const { addItem } = useCart();

    return (
        <article style={{
            border: '1px solid #333333', borderRadius: '8px',
            padding: '1rem', background: '#27272a'
        }}>
            <h3 style={{ margin: '0 0 0.5rem' }}>{produto.nome}</h3>
            <p style={{ color: '#a1a1aa', margin: '0 0 0.5rem', fontSize: '0.9rem' }}>
                {produto.descricao}
            </p>
            <p style={{ fontSize: '1.25rem', fontWeight: 'bold', color: '#22c55e', margin: '0' }}>
                R$ {Number(produto.preco).toFixed(2)}
            </p>
            <button onClick={() => addItem(produto)} style={{
                marginTop: '0.75rem', background: '#15803d', color: 'white',
                border: 'none', padding: '0.5rem 1rem',
                borderRadius: '6px', cursor: 'pointer', width: '100%'
            }}>
                Adicionar ao carrinho
            </button>
        </article>
    );
}
