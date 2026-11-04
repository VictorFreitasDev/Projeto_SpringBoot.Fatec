import { useCart } from '../context/CartContext';

export default function CartPage() {
    const { items, removeItem, totalPrice } = useCart();

    if (items.length === 0) {
        return <p>Seu carrinho está vazio. Volte ao catálogo!</p>;
    }

    return (
        <>
            <h1>Seu carrinho</h1>
            {items.map(item => (
                <div key={item.produtoId} style={{
                    display: 'flex', justifyContent: 'space-between',
                    padding: '0.6rem 0', borderBottom: '1px solid #333333'
                }}>
                    <span>{item.nome} — {item.quantidade}x R$ {Number(item.preco).toFixed(2)}</span>
                    <button onClick={() => removeItem(item.produtoId)}
                            style={{
                                background: '#3f3f46', color: '#e4e4e7',
                                border: '1px solid #52525b', borderRadius: '4px',
                                padding: '0.2rem 0.5rem', cursor: 'pointer'
                            }}>
                        Remover
                    </button>
                </div>
            ))}
            <p style={{ fontSize: '1.2rem', marginTop: '1rem' }}>
                <strong>Total: R$ {totalPrice.toFixed(2)}</strong>
            </p>
        </>
    );
}

