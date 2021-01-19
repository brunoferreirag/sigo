import { DataSource } from '@angular/cdk/table';
import { CollectionViewer } from '@angular/cdk/collections';
import { Observable, BehaviorSubject, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";
import { Usuario } from 'src/app/models/usuario';
import { UsuarioService } from '../usuario.service';
import { UsuarioListResponse } from 'src/app/models/usuario-response';
 
export class UsuarioDataSource implements DataSource<Usuario>{
 
    private usuarioSubject = new BehaviorSubject<Usuario[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private countSubject = new BehaviorSubject<number>(0);
    public counter$ = this.countSubject.asObservable();
 
    constructor(private usuarioService: UsuarioService) { }
 
    connect(collectionViewer: CollectionViewer): Observable<Usuario[]> {
        return this.usuarioSubject.asObservable();
    }
 
    disconnect(collectionViewer: CollectionViewer): void {
        this.usuarioSubject.complete();
        this.loadingSubject.complete();
        this.countSubject.complete();
    }
 
    loadUsuarios(page = 0, size = 10) {
        this.loadingSubject.next(true);
        this.usuarioService.getAllUsuarios( page, size )
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe((result: UsuarioListResponse) => {
                this.usuarioSubject.next(result.content);
                this.countSubject.next(result.totalElements);
            }
            );
    }
 
}