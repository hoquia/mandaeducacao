import { INotasGeralDisciplina } from './../entities/notas-geral-disciplina/notas-geral-disciplina.model';
import { IDocente } from './../entities/docente/docente.model';
import { ITurma } from './../entities/turma/turma.model';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { DiscenteService } from 'app/entities/discente/service/discente.service';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { NotasGeralDisciplinaService } from 'app/entities/notas-geral-disciplina/service/notas-geral-disciplina.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  turmas: ITurma[] = [];

  totalTurmas = 0;
  totalTurmaManha = 0;
  totalTurmaTarde = 0;
  totalTurmaNoite = 0;
  // DISCENTES
  totalAlunoGeral = 0;
  totalAlunosManha = 0;
  totalAlunosTarde = 0;
  totalAlunosNoite = 0;
  totalAlunosManhaMasculino = 0;
  totalAlunosTardeMasculino = 0;
  totalAlunosNoiteMasculino = 0;
  totalAlunosManhaFeminino = 0;
  totalAlunosTardeFeminino = 0;
  totalAlunosNoiteFeminino = 0;
  // PROFESSORES
  totalProfessores = 0;
  totalProfessoresManha = 0;
  totalProfessoresTarde = 0;
  totalProfessoresNoite = 0;
  totalProfessoresManhaMasculino = 0;
  totalProfessoresTardeMasculino = 0;
  totalProfessoresNoiteMasculino = 0;
  totalProfessoresManhaFeminino = 0;
  totalProfessoresTardeFeminino = 0;
  totalProfessoresNoiteFeminino = 0;
  // MATRICULAS
  totalMatriculas = 0;
  totalMatriculasManha = 0;
  totalMatriculasTarde = 0;
  totalMatriculasNoite = 0;
  totalManhaMatriculasMasculino = 0;
  totalTardeMatriculasMasculino = 0;
  totalNoiteMatriculasMasculino = 0;
  totalManhaMatriculasFeminino = 0;
  totalTardeMatriculasFeminino = 0;
  totalNoiteMatriculasFeminino = 0;
  // CONFIRMACOES
  totalConfirmacoes = 0;
  totalConfirmacoesManha = 0;
  totalConfirmacoesTarde = 0;
  totalConfirmacoesNoite = 0;
  totalConfirmacoesManhaMasculino = 0;
  totalConfirmacoesTardeMasculino = 0;
  totalConfirmacoesNoiteMasculino = 0;
  totalConfirmacoesManhaFemenino = 0;
  totalConfirmacoesTardeFemenino = 0;
  totalConfirmacoesNoiteFemenino = 0;
  // DOCENTES
  professoresTelemovel: IDocente[] = [];

  // NOTAS GERAIS
  notasGeraisDisciplina: INotasGeralDisciplina[] = [];

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private router: Router,
    private turmaServ: TurmaService,
    private matriculaServ: MatriculaService,
    private discenteServ: DiscenteService,
    private docenteServ: DocenteService,
    private notasGeralServ: NotasGeralDisciplinaService
  ) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    this.getTotalTurmas();
    this.getTotalTurmasManha();
    this.getTotalTurmastarde();
    this.getTotalTurmasNoite();
    this.getTotalDiscentes();
    this.getTotalAlunosManha();
    this.getTotalAlunosTarde();
    this.getTotalAlunosNoite();
    this.getTotalAlunosManhaMasculino();
    this.getTotalAlunosTardeMasculino();
    this.getTotalAlunosNoiteMasculino();
    this.getTotalAlunosManhaFeminino();
    this.getTotalAlunosTardeFeminino();
    this.getTotalAlunosNoiteFeminino();
    this.getTotalMatriculas();
    this.getTotalMatriuclasManha();
    this.getTotalMatriculasTarde();
    this.getTotalMatriculasNoite();
    this.getTotalMatriuclasManhaFemenino();
    this.getTotalMatriuclasTardeFemenino();
    this.getTotalMatriuclasNoiteFemenino();
    this.getTotalMatriuclasManhaMasculino();
    this.getTotalMatriuclasTardeMasculino();
    this.getTotalMatriuclasNoiteMasculino();
    this.getTotalConfirmacoes();
    this.getTotalConfirmacoesManha();
    this.getTotalConfirmacoesTarde();
    this.getTotalConfirmacoesNoite();
    this.getTotalConfirmacoesManhaMasculino();
    this.getTotalConfirmacoesTardeMasculino();
    this.getTotalConfirmacoesNoiteMasculino();
    this.getTotalConfirmacoesManhaFeminino();
    this.getTotalConfirmacoesTardeFeminino();
    this.getTotalConfirmacoesNoiteFeminino();
    this.getTotalDocentes();
    this.getNotasGerais();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  getTotalTurmas(): void {
    this.turmaServ.getTurmas().subscribe(turmasResult => (this.totalTurmas = turmasResult.length));
  }

  getTotalTurmasManha(): void {
    this.turmaServ.getTurmas().subscribe(turmasResult => {
      this.totalTurmaManha = turmasResult.filter(turma => turma.turno?.nome === 'Manhã').length;
    });
  }
  getTotalTurmastarde(): void {
    this.turmaServ.getTurmas().subscribe(turmasResult => {
      this.totalTurmaTarde = turmasResult.filter(turma => turma.turno?.nome === 'Tarde').length;
    });
  }
  getTotalTurmasNoite(): void {
    this.turmaServ.getTurmas().subscribe(turmasResult => {
      this.totalTurmaNoite = turmasResult.filter(turma => turma.turno?.nome === 'Noite').length;
    });
  }

  getTotalDiscentes(): void {
    this.discenteServ.getDiscentes().subscribe(discentes => (this.totalAlunoGeral = discentes.length));
  }
  getTotalAlunosManha(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalAlunosManha = matriculasResult.filter(matricula => matricula.turma?.turno?.nome === 'Manhã').length;
    });
  }
  getTotalAlunosTarde(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalAlunosTarde = matriculasResult.filter(matricula => matricula.turma?.turno?.nome === 'Tarde').length;
    });
  }
  getTotalAlunosNoite(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalAlunosNoite = matriculasResult.filter(matricula => matricula.turma?.turno?.nome === 'Noite').length;
    });
  }
  getTotalAlunosManhaMasculino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalAlunosManhaMasculino = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Manhã' && matricula.discente?.sexo === 'MASCULINO'
      ).length;
    });
  }
  getTotalAlunosTardeMasculino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalAlunosTardeMasculino = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Tarde' && matricula.discente?.sexo === 'MASCULINO'
      ).length;
    });
  }
  getTotalAlunosNoiteMasculino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalAlunosNoiteMasculino = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Noite' && matricula.discente?.sexo === 'MASCULINO'
      ).length;
    });
  }
  getTotalAlunosManhaFeminino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalAlunosManhaFeminino = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Manhã' && matricula.discente?.sexo === 'FEMENINO'
      ).length;
    });
  }
  getTotalAlunosTardeFeminino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalAlunosTardeFeminino = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Tarde' && matricula.discente?.sexo === 'FEMENINO'
      ).length;
    });
  }
  getTotalAlunosNoiteFeminino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalAlunosNoiteFeminino = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Noite' && matricula.discente?.sexo === 'FEMENINO'
      ).length;
    });
  }
  // MATRICULAS
  getTotalMatriculas(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculas => {
      this.totalMatriculas = matriculas.filter(matricula => matricula.estado === 'MATRICULADO').length;
    });
  }
  getTotalMatriuclasManha(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalMatriculasManha = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Manhã' && matricula.estado === 'MATRICULADO'
      ).length;
    });
  }

  getTotalMatriculasTarde(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalMatriculasTarde = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Tarde' && matricula.estado === 'MATRICULADO'
      ).length;
    });
  }
  getTotalMatriculasNoite(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalMatriculasNoite = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Noite' && matricula.estado === 'MATRICULADO'
      ).length;
    });
  }

  getTotalMatriuclasManhaMasculino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalManhaMatriculasMasculino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Manhã' && matricula.estado === 'MATRICULADO' && matricula.discente?.sexo === 'MASCULINO'
      ).length;
    });
  }
  getTotalMatriuclasTardeMasculino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalTardeMatriculasMasculino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Tarde' && matricula.estado === 'MATRICULADO' && matricula.discente?.sexo === 'MASCULINO'
      ).length;
    });
  }
  getTotalMatriuclasNoiteMasculino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalNoiteMatriculasMasculino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Noite' && matricula.estado === 'MATRICULADO' && matricula.discente?.sexo === 'MASCULINO'
      ).length;
    });
  }
  getTotalMatriuclasManhaFemenino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalManhaMatriculasFeminino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Manhã' && matricula.estado === 'MATRICULADO' && matricula.discente?.sexo === 'FEMENINO'
      ).length;
    });
  }
  getTotalMatriuclasTardeFemenino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalTardeMatriculasFeminino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Tarde' && matricula.estado === 'MATRICULADO' && matricula.discente?.sexo === 'FEMENINO'
      ).length;
    });
  }
  getTotalMatriuclasNoiteFemenino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalNoiteMatriculasFeminino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Noite' && matricula.estado === 'MATRICULADO' && matricula.discente?.sexo === 'FEMENINO'
      ).length;
    });
  }

  // CONFIRMACOES
  getTotalConfirmacoes(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculas => {
      this.totalConfirmacoes = matriculas.filter(matricula => matricula.estado === 'CONFIRMADO').length;
    });
  }

  getTotalConfirmacoesManha(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalConfirmacoesManha = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Manhã' && matricula.estado === 'CONFIRMADO'
      ).length;
    });
  }
  getTotalConfirmacoesTarde(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalConfirmacoesTarde = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Tarde' && matricula.estado === 'CONFIRMADO'
      ).length;
    });
  }
  getTotalConfirmacoesNoite(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalConfirmacoesNoite = matriculasResult.filter(
        matricula => matricula.turma?.turno?.nome === 'Noite' && matricula.estado === 'CONFIRMADO'
      ).length;
    });
  }

  getTotalConfirmacoesManhaMasculino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalConfirmacoesManhaMasculino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Manhã' && matricula.estado === 'CONFIRMADO' && matricula.discente?.sexo === 'MASCULINO'
      ).length;
    });
  }
  getTotalConfirmacoesTardeMasculino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalConfirmacoesTardeMasculino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Tarde' && matricula.estado === 'CONFIRMADO' && matricula.discente?.sexo === 'MASCULINO'
      ).length;
    });
  }
  getTotalConfirmacoesNoiteMasculino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalConfirmacoesNoiteMasculino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Noite' && matricula.estado === 'CONFIRMADO' && matricula.discente?.sexo === 'MASCULINO'
      ).length;
    });
  }
  getTotalConfirmacoesManhaFeminino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalConfirmacoesManhaFemenino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Manhã' && matricula.estado === 'CONFIRMADO' && matricula.discente?.sexo === 'FEMENINO'
      ).length;
    });
  }
  getTotalConfirmacoesTardeFeminino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalConfirmacoesTardeFemenino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Tarde' && matricula.estado === 'CONFIRMADO' && matricula.discente?.sexo === 'FEMENINO'
      ).length;
    });
  }
  getTotalConfirmacoesNoiteFeminino(): void {
    this.matriculaServ.getMatriculas().subscribe(matriculasResult => {
      this.totalConfirmacoesNoiteFemenino = matriculasResult.filter(
        matricula =>
          matricula.turma?.turno?.nome === 'Noite' && matricula.estado === 'CONFIRMADO' && matricula.discente?.sexo === 'FEMENINO'
      ).length;
    });
  }

  getTotalDocentes(): void {
    this.docenteServ.getDiscentes().subscribe(doc => (this.professoresTelemovel = doc));
    this.docenteServ.getDiscentes().subscribe(doc => (this.totalProfessores = doc.length));
  }

  getNotasGerais(): void {
    this.notasGeralServ.getNotasGerais().subscribe(ntgd => (this.notasGeraisDisciplina = ntgd));
  }
}
