import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AlertaService } from '../shared/service/alerta-service.service';
import { AutenticacaoService } from '../shared/service/autenticacao.service';

@Component({
  selector: 'app-root',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  formLogin: FormGroup;
  loginInvalido: Boolean;

  constructor(public formBuilder: FormBuilder, public autenticacaoService: AutenticacaoService,public route: ActivatedRoute,
    public router: Router, public alertaService: AlertaService) { 
    this.formLogin = this.formBuilder.group({
      username: [null, [Validators.required, Validators.maxLength(30)]],
      senha: [null, [Validators.required, Validators.minLength(3),Validators.maxLength(20)]]
    });
  }

  ngOnInit(): void {
  }

  login(): void {
    this.loginInvalido = false;
    this.autenticacaoService
      .login(this.formLogin.get('username').value, this.formLogin.get('senha').value)
      .pipe(first())
      .subscribe(res =>{
        this.router.navigate(["/"]);
      });
  }

}
