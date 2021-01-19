import { DataSource } from '@angular/cdk/table';
import { CollectionViewer } from '@angular/cdk/collections';
import { Observable, BehaviorSubject, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";
import { Armazem } from 'src/app/models/armazem';
import { ArmazemService } from '../armazem.service';
import { ArmazemListResponse } from 'src/app/models/armazem-list-response';
 
export class ArmazemDataSource implements DataSource<Armazem>{
 
    private armazemSubject = new BehaviorSubject<Armazem[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private countSubject = new BehaviorSubject<number>(0);
    public counter$ = this.countSubject.asObservable();
 
    constructor(private armazemService: ArmazemService) { }
 
    connect(collectionViewer: CollectionViewer): Observable<Armazem[]> {
        return this.armazemSubject.asObservable();
    }
 
    disconnect(collectionViewer: CollectionViewer): void {
        this.armazemSubject.complete();
        this.loadingSubject.complete();
        this.countSubject.complete();
    }
 
    loadArmazens(page = 0, size = 10) {
        this.loadingSubject.next(true);
        this.armazemService.getAllArmazens( page, size )
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe((result: ArmazemListResponse) => {
                this.armazemSubject.next(result.content);
                this.countSubject.next(result.totalElements);
            }
            );
    }
 
}