const API_BASE = 'http://localhost:8083';

export async function listarProdutos() {
    const response = await fetch(`${API_BASE}/produtos`);
    if (!response.ok) {
        throw new Error(`Erro ao listar produtos: ${response.status}`);
    }
    return response.json();
}

