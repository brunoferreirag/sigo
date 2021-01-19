import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AutenticacaoService } from "../shared/service/autenticacao.service";
import { AlertaService } from '../shared/service/alerta-service.service';
import { Router } from '@angular/router';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private autenticacaoService: AutenticacaoService, private alertaService: AlertaService, public router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if (err.status === 401 && this.router.url !== '/login') {
                this.autenticacaoService.logout();
                this.router.navigate(["/"]);
            }

            const error =err.error.message || (err.error.length>0 && err.error[0].message) || err.statusText;
            this.alertaService.error(error);
            return throwError(error);
        }))
    }
}