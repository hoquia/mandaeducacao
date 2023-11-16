import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  totalTurmas = 24;
  totalTurmaManha = 10;
  totalTurmaTarde = 10;
  totalTurmaNoite = 4;
  totalAlunoGeral = 1000;
  totalAlunoManha = 500;
  totalAlunoTarde = 250;
  totalAlunoNoite = 250;
  // PROFESSORES
  totalProfessores = 50;
  totalProfessoresManha = 20;
  totalProfessoresTarde = 20;
  totalProfessoresNoite = 10;
  totalProfessoresManhaMasculino = 15;
  totalProfessoresTardeMasculino = 10;
  totalProfessoresNoiteMasculino = 9;
  totalProfessoresManhaFeminino = 5;
  totalProfessoresTardeFeminino = 10;
  totalProfessoresNoiteFeminino = 1;
  // MATRICULAS
  totalMatriculas = 2000;
  totalMatriculasManha = 500;
  totalMatriculasTarde = 250;
  totalMatriculasNoite = 250;
  totalManhaMasculino = 200;
  totalTardeMasculino = 100;
  totalNoiteMasculino = 200;
  totalManhaFeminino = 300;
  totalTardeFeminino = 150;
  totalNoiteFeminino = 50;
  // DOCENTES
  professoresTelemovel: any[] = [
    { nome: 'Fulano 1', disciplina: 'Matematica', telemovel: '999 999 999', email: 'email@mail.com' },
    { nome: 'Docente', disciplina: 'Fisica', telemovel: '222 222 222', email: 'mail@gmail.com' },
    { nome: 'Professor', disciplina: 'Quimica', telemovel: '111 111 111', email: 'gmail@hotmail.com' },
  ];

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
