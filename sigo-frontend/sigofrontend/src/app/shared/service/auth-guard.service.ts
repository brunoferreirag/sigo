import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router } from "@angular/router";
import { AutenticacaoService } from "./autenticacao.service";

@Injectable()
export class AuthGuardService implements CanActivate {
    constructor(public auth: AutenticacaoService, public router: Router) { }
    canActivate(route: ActivatedRouteSnapshot): boolean {
        if (!this.auth.usuarioAtualValue) {
            this.router.navigate(['login']);
            return false;
        }
        const expectedRole = route.data.expectedRole;
        if(expectedRole){
            if (!this.auth.podeAcessar(expectedRole))
            {
               this.router.navigate(['login']);
               return false;
           }
        }
        
        return true;
    }
}