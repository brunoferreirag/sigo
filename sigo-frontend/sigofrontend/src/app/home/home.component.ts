import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticacaoResponse } from '../models/autenticacao-response';
import { AutenticacaoService } from '../shared/service/autenticacao.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  usuarioAtual: AutenticacaoResponse;
  constructor(public autenticacaoService: AutenticacaoService, public router: Router) {
    this.usuarioAtual = autenticacaoService.usuarioAtualValue
  }

  ngOnInit() {
    if (!this.usuarioAtual) {
      this.router.navigate(['/login']);
    }
  }

}
