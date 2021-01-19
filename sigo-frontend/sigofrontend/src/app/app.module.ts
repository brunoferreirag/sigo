import { BrowserModule } from '@angular/platform-browser';
import { InjectionToken, NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AuthGuardService } from './shared/service/auth-guard.service';

import {
  MatToolbarModule,
  MatMenuModule,
  MatIconModule,
  MatButtonModule,
  MatTableModule,
  MatDividerModule,
  MatProgressSpinnerModule,
  MatInputModule,
  MatCardModule,
  MatSlideToggleModule,
  MatSelectModule,
  MatOptionModule,
  MatPaginatorModule,
  MatDialogModule,

  MAT_DIALOG_DEFAULT_OPTIONS,
  MatListModule,
  MatCheckboxModule
} from '@angular/material';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { AlertaComponent } from './alerta/alerta.component';
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { UsuarioListagemComponent } from './usuario/listagem/usuario-listagem.component';
import { ErrorInterceptor } from './helpers/error.interceptor';
import { LoadHttpInterceptor } from './helpers/load.interceptor';
import { ModalComponent } from './shared/component/modal.component';
import { UsuarioFormularioComponent } from './usuario/formulario/usuario-formulario.component';
import { ArmazemListagemComponent } from './armazem/listagem/armazem-listagem.component';
export const DEFAULT_TIMEOUT = new InjectionToken<number>('defaultTimeout');
@NgModule({
  declarations: [
    AppComponent,
    ModalComponent,
    LoginComponent,
    HomeComponent,
    AlertaComponent,
    UsuarioListagemComponent,
    UsuarioFormularioComponent,
    ArmazemListagemComponent
  ],
  entryComponents: [ModalComponent],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    AppRoutingModule,
    FlexLayoutModule,
    FormsModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatInputModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatDividerModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatOptionModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    MatDialogModule,
    MatListModule,
    MatCheckboxModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: LoadHttpInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }, 
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    AuthGuardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
