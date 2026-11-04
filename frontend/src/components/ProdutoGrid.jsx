import { useState, useEffect } from 'react';
import { listarProdutos } from '../services/produtoService';
import ProdutoCard from './ProdutoCard';

export default function ProdutoGrid() {
    const [produtos, setProdutos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState(null);

    useEffect(() => {
        listarProdutos()
            .then(setProdutos)
            .catch(e => setErro(e.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <p>Carregando produtos…</p>;
    if (erro) return <p style={{ color: '#ef4444' }}>{erro}</p>;
    if (produtos.length === 0) return <p>Nenhum produto disponível.</p>;

    return (
        <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fill, minmax(240px, 1fr))',
            gap: '1rem'
        }}>
            {produtos.map(p => <ProdutoCard key={p.id} produto={p} />)}
        </div>
    );
}

