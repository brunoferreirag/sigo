import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AutenticacaoResponse } from 'src/app/models/autenticacao-response';


@Injectable({
  providedIn: 'root'
})
export class AutenticacaoService {
  private currentUserSubject: BehaviorSubject<AutenticacaoResponse>;
  public currentUser: Observable<AutenticacaoResponse>;
  constructor(public http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<AutenticacaoResponse>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  login(username: string, password: string) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Accept': 'application/json' });
    return this.http.post<AutenticacaoResponse>(environment.wsLogin, JSON.stringify({ username, password }), { headers })
      .pipe(map(user => {
        sessionStorage.setItem('usuarioAtual', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

  logout(): void {
    sessionStorage.removeItem('usuarioAtual');
    this.currentUserSubject.next(null);
  }

  public get usuarioAtualValue(): AutenticacaoResponse {
    return this.currentUserSubject.value;
  }

  public podeAcessar(funcao:string) : boolean{
    return this.usuarioAtualValue.funcoes.some( x => x === funcao);
  }
}
