import { Component, OnInit } from "@angular/core";
import { FormArray, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ThemePalette } from "@angular/material";
import { ActivatedRoute, Router } from "@angular/router";
import { Funcao } from "src/app/models/funcao";
import { Usuario } from "src/app/models/usuario";
import { AlertaService } from "src/app/shared/service/alerta-service.service";
import { UsuarioService } from "../usuario.service";
export interface Task {
  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: Task[];
}
@Component({
    selector: 'app-usuario-formulario',
    templateUrl: './usuario-formulario.component.html',
    styleUrls: ['./usuario-formulario.component.css']
  })
  export class UsuarioFormularioComponent implements OnInit {
    formUsuario: FormGroup;
    funcoes: Array<Funcao>;
    userName: string;
  
    escolherTodasAsFuncoes() {
      this.funcoes.every(f => f.escolhida);
    }

    constructor(public formBuilder: FormBuilder, private alertaService: AlertaService, private usuarioService: UsuarioService, private route:ActivatedRoute,  public router: Router){
      this.formUsuario = this.formBuilder.group({
        username: [null, [Validators.required, Validators.maxLength(30)]],
        senha: [null, [Validators.required, Validators.minLength(3),Validators.maxLength(20)]],
        primeiroNome: [null, [Validators.required, Validators.maxLength(50)]],
        ultimoNome: [null, [Validators.required, Validators.minLength(3),Validators.maxLength(50)]],
        funcoes: new FormArray([]),
      });

      this.userName = this.route.snapshot.paramMap.get('id');
    }
    ngOnInit(): void {
       this.usuarioService.getAllFuncoes().subscribe(funcoes =>{
         this.funcoes = funcoes;
         if(this.userName){
           this.usuarioService.getUsuarioByUsername(this.userName).subscribe(result =>{
             this.formUsuario.get("username").setValue(result.username);
             this.formUsuario.get("primeiroNome").setValue(result['primeiro-nome']);
             this.formUsuario.get("ultimoNome").setValue(result['ultimo-nome']);

             result.funcoes.forEach(t => {
               const funcaoEncontrada = this.funcoes.filter(p => p.id ===t.id);
               if(funcaoEncontrada){
                 funcaoEncontrada[0].escolhida = true;
               }
             })

           })
         }
       })
    }

    salvar(): void {
       const usuario : Usuario = new Usuario();
       usuario.password= this.formUsuario.get('senha').value;
       usuario['primeiro-nome'] = this.formUsuario.get('primeiroNome').value;
       usuario['ultimo-nome'] = this.formUsuario.get('ultimoNome').value;
       const funcoesEscolhidas = this.funcoes.filter(p=>p.escolhida === true);
       usuario.funcoes = funcoesEscolhidas;
       if(this.userName){
         usuario.username= this.userName;
         this.usuarioService.editar(usuario).subscribe((result) =>{
           this.alertaService.success("Usuário editado com sucesso!",true);
           this.router.navigate(['/usuarios']);
         })
       }
       else{
        usuario.username= this.formUsuario.get('username').value;
        this.usuarioService.incluir(usuario).subscribe((result) =>{
          this.alertaService.success("Usuário incluído com sucesso!",true);
          this.router.navigate(['/usuarios']);
        })
       }
    }

    cancelar(){
      this.router.navigate(['/usuarios']);
    }
    
  }