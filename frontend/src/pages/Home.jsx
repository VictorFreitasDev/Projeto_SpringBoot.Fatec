import ProdutoGrid from '../components/ProdutoGrid';

export default function Home() {
    return (
        <>
            <h1>Produtos da feira</h1>
            <p style={{ color: '#a1a1aa' }}>
                Direto do produtor local para a sua mesa.
            </p>
            <ProdutoGrid />
        </>
    );
}

