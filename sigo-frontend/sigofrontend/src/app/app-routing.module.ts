import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArmazemListagemComponent } from './armazem/listagem/armazem-listagem.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AuthGuardService } from './shared/service/auth-guard.service';
import { UsuarioFormularioComponent } from './usuario/formulario/usuario-formulario.component';
import { UsuarioListagemComponent } from './usuario/listagem/usuario-listagem.component';
const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: LoginComponent },
  {
    path: 'usuarios', component: UsuarioListagemComponent, canActivate: [AuthGuardService], data: {
      expectedRole: 'ROLE_ADMIN_SIGO'
    }
  },
  {
    path: 'usuario/:id', component: UsuarioFormularioComponent, canActivate: [AuthGuardService], data: {
      expectedRole: 'ROLE_ADMIN_SIGO'
    }
  },
  {
    path: 'usuario', component: UsuarioFormularioComponent, canActivate: [AuthGuardService], data: {
      expectedRole: 'ROLE_ADMIN_SIGO'
    }
  },
  {
    path: 'armazens', component: ArmazemListagemComponent, canActivate: [AuthGuardService], data: {
      expectedRole: 'ROLE_SIGO_GPI'
    }
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
