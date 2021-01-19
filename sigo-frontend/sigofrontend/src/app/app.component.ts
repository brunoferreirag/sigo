import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticacaoService } from './shared/service/autenticacao.service';
import { SpinnerService } from './shared/service/spinner.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'SIGO - Sistema Integrado de Gestão e Operação';
  constructor(public autenticacaoService: AutenticacaoService, public router: Router, public spinnerService: SpinnerService){}

  public get estaLogado(): boolean {
    if(this.autenticacaoService.usuarioAtualValue){
      return true;
    }
    return false;
  }
  logout (): void {
    this.autenticacaoService.logout();
    this.router.navigate(['/login']);
  }

  podeAcessar(role : string) : boolean {
    return this.autenticacaoService.podeAcessar(role);
  }
}
