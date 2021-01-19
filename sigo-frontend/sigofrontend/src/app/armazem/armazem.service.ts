import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { ArmazemListResponse } from "../models/armazem-list-response";

@Injectable({
    providedIn: 'root'
})
export class ArmazemService {
    constructor(public http: HttpClient) { }

    /**
 * Consulta os armazéns no sistema
 */
    getAllArmazens(page: number, size: number): Observable<ArmazemListResponse> {
        return this.http.get<ArmazemListResponse>(environment.wsListarArmazem + '?page=' + page + '&size=' + size);
    }

      /**
   * Excluir Armazem
   * @param id do armazém para exclusão 
   */
  excluirArmazem(id:string): Observable<any> {
    return this.http.delete<any>(environment.wsListarUsuarios+'/'+id);
  }

}