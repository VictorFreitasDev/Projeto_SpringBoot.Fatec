import { createContext, useContext, useState } from 'react';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);   // { token, clienteId, email }

    function login(token, clienteId, email) {
        setUser({ token, clienteId, email });
        // AULA 19: persistir em sessionStorage + chamar POST /auth/login
    }

    function logout() {
        setUser(null);
    }

    return (
        <AuthContext.Provider value={{ user, autenticado: !!user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const ctx = useContext(AuthContext);
    if (!ctx) throw new Error('useAuth deve ser usado dentro de AuthProvider');
    return ctx;
}

