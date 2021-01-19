import { Component, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";

export interface DialogData {
    mensagem: string;
    titulo:string
  }

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.css']
  })
  export class ModalComponent {
  
    constructor(
      public dialogRef: MatDialogRef<ModalComponent>,
      @Inject(MAT_DIALOG_DATA) public data: DialogData) {}
  
    ok(): void {
      this.dialogRef.close({});
    }

    cancelar(): void {
      this.dialogRef.close(null);
    }
  
  }