import { createContext, useContext, useState } from 'react';

const CartContext = createContext(null);

export function CartProvider({ children }) {
    const [items, setItems] = useState([]);

    function addItem(produto, quantidade = 1) {
        setItems(atual => {
            const existente = atual.find(i => i.produtoId === produto.id);
            if (existente) {
                return atual.map(i =>
                    i.produtoId === produto.id
                        ? { ...i, quantidade: i.quantidade + quantidade }
                        : i
                );
            }
            return [...atual, {
                produtoId: produto.id,
                nome: produto.nome,
                preco: produto.preco,
                quantidade
            }];
        });
    }

    function removeItem(produtoId) {
        setItems(atual => atual.filter(i => i.produtoId !== produtoId));
    }

    const totalItems = items.reduce((soma, i) => soma + i.quantidade, 0);
    const totalPrice = items.reduce((soma, i) => soma + i.quantidade * Number(i.preco), 0);

    return (
        <CartContext.Provider value={{ items, addItem, removeItem, totalItems, totalPrice }}>
            {children}
        </CartContext.Provider>
    );
}

export function useCart() {
    const ctx = useContext(CartContext);
    if (!ctx) throw new Error('useCart deve ser usado dentro de CartProvider');
    return ctx;
}

