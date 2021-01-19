import { Usuario } from "./usuario";

export interface UsuarioListResponse {
    content: Usuario[];
    totalElements: number;
}