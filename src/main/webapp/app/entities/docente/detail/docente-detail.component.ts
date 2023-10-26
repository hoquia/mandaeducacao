import { DocenteService } from './../service/docente.service';
import { NotasPeriodicaDisciplinaService } from 'app/entities/notas-periodica-disciplina/service/notas-periodica-disciplina.service';
import { IHorario } from './../../horario/horario.model';
import { HorarioService } from 'app/entities/horario/service/horario.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocente } from '../docente.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { INotasPeriodicaDisciplina } from 'app/entities/notas-periodica-disciplina/notas-periodica-disciplina.model';

@Component({
  selector: 'app-docente-detail',
  templateUrl: './docente-detail.component.html',
})
export class DocenteDetailComponent implements OnInit {
  docente: IDocente | null = null;
  horarioSharedCollection: IHorario[] = [];
  periodosSharedCollection: INotasPeriodicaDisciplina[] = [];
  periodoSelecionado = 0;
  horarioSelecionado: IHorario | any = null;
  disciplinaCurricularSelecionada = [];

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected horario: HorarioService,
    protected notaPeriodicaService: NotasPeriodicaDisciplinaService,
    protected docenteService: DocenteService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docente }) => {
      this.docente = docente;
    });

    this.notaPeriodicaService.query({ size: 3 }).subscribe(res => {
      this.periodosSharedCollection = res.body ?? [];
    });

    // const docenteID = Number(this.activatedRoute.snapshot.queryParamMap.get('docente_id'));

    this.horario.query({ size: 100000 }).subscribe(res => {
      this.horarioSharedCollection = res.body ?? [];
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }

  protected getHorarioProfessor(docenteID: number): void {
    alert(docenteID);
    // const facturaID = Number(this.editForm.get('factura')?.value?.id);

    // alert(facturaID);

    this.horario.query().subscribe(res => {
      this.horarioSharedCollection = res.body?.filter(x => x.docente?.id === docenteID) ?? [];
    });
  }

  protected gerarListaPagoNaoPago(horarioSelecionado: IHorario): void {
    // alert(this.periodoSelecionado.toString());
    // eslint-disable-next-line no-console
    const horarioID = Number(horarioSelecionado.id);
    this.docenteService.downloadListaPagoNaoPagoPdf(horarioID, this.periodoSelecionado).subscribe(res => {
      const url = window.URL.createObjectURL(res);
      const a = document.createElement('a');
      a.href = url;
      a.target = '_blank';
      // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
      a.title = `mini-pauta-${horarioSelecionado}`;
      a.rel = 'noopener noreferrer';
      a.click();
      window.URL.revokeObjectURL(url);
      a.remove();
    });
  }
}
