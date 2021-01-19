import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Funcao } from '../models/funcao';
import { Usuario } from '../models/usuario';
import { UsuarioListResponse } from '../models/usuario-response';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(public http: HttpClient) { }

    /**
   * Consulta os usuários no sistema
   */
  getAllUsuarios(page: number, size: number): Observable<UsuarioListResponse> {
    return this.http.get<UsuarioListResponse>(environment.wsListarUsuarios+'?page='+page+'&size='+size);
  }

   /**
   * Consulta os usuários no sistema
   */
  getUsuarioByUsername(userName:string): Observable<Usuario> {
    return this.http.get<Usuario>(environment.wsListarUsuarios+'/'+userName);
  }

  /**
   * Editar um usuario
   */
  editar(usuario:Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(environment.wsListarUsuarios+'/'+usuario.username,usuario);
  }

   /**
   * Editar um usuario
   */
  incluir(usuario:Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(environment.wsListarUsuarios,usuario);
  }


   
  /**
   * Excluir usuário
   * @param userName username do usuário para exclusão 
   */
  excluirUsuarios(userName:string): Observable<any> {
    return this.http.delete<any>(environment.wsListarUsuarios+'/'+userName);
  }

    /**
   * Consulta as funções que um usuário pode realizar
   */
  getAllFuncoes(): Observable<Array<Funcao>> {
    return this.http.get<Array<Funcao>>(environment.wsListarFuncoes);
  }


}
