import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AlertaService } from '../shared/service/alerta-service.service';

@Component({
  selector: 'app-alerta',
  templateUrl: './alerta.component.html',
  styleUrls: ['./alerta.component.css']
})
export class AlertaComponent implements OnInit {
  private subscription: Subscription;
  mensagem: any;
  constructor(private alertaService: AlertaService) { }

  ngOnInit() {
    this.subscription = this.alertaService.getAlert()
    .subscribe(mensagem => {
        switch (mensagem && mensagem.type) {
            case 'success':
                mensagem.cssClass = 'alert alert-success';
                break;
            case 'error':
                mensagem.cssClass = 'alert alert-danger';
                break;
        }

        this.mensagem = mensagem;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
}

}
