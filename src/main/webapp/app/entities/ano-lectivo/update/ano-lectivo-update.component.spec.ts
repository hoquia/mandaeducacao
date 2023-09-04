import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AnoLectivoFormService } from './ano-lectivo-form.service';
import { AnoLectivoService } from '../service/ano-lectivo.service';
import { IAnoLectivo } from '../ano-lectivo.model';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { INivelEnsino } from 'app/entities/nivel-ensino/nivel-ensino.model';
import { NivelEnsinoService } from 'app/entities/nivel-ensino/service/nivel-ensino.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IHorario } from 'app/entities/horario/horario.model';
import { HorarioService } from 'app/entities/horario/service/horario.service';
import { IPlanoAula } from 'app/entities/plano-aula/plano-aula.model';
import { PlanoAulaService } from 'app/entities/plano-aula/service/plano-aula.service';
import { ILicao } from 'app/entities/licao/licao.model';
import { LicaoService } from 'app/entities/licao/service/licao.service';
import { IProcessoSelectivoMatricula } from 'app/entities/processo-selectivo-matricula/processo-selectivo-matricula.model';
import { ProcessoSelectivoMatriculaService } from 'app/entities/processo-selectivo-matricula/service/processo-selectivo-matricula.service';
import { IOcorrencia } from 'app/entities/ocorrencia/ocorrencia.model';
import { OcorrenciaService } from 'app/entities/ocorrencia/service/ocorrencia.service';
import { INotasPeriodicaDisciplina } from 'app/entities/notas-periodica-disciplina/notas-periodica-disciplina.model';
import { NotasPeriodicaDisciplinaService } from 'app/entities/notas-periodica-disciplina/service/notas-periodica-disciplina.service';
import { INotasGeralDisciplina } from 'app/entities/notas-geral-disciplina/notas-geral-disciplina.model';
import { NotasGeralDisciplinaService } from 'app/entities/notas-geral-disciplina/service/notas-geral-disciplina.service';
import { IDissertacaoFinalCurso } from 'app/entities/dissertacao-final-curso/dissertacao-final-curso.model';
import { DissertacaoFinalCursoService } from 'app/entities/dissertacao-final-curso/service/dissertacao-final-curso.service';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';
import { IRecibo } from 'app/entities/recibo/recibo.model';
import { ReciboService } from 'app/entities/recibo/service/recibo.service';
import { IResponsavelTurno } from 'app/entities/responsavel-turno/responsavel-turno.model';
import { ResponsavelTurnoService } from 'app/entities/responsavel-turno/service/responsavel-turno.service';
import { IResponsavelAreaFormacao } from 'app/entities/responsavel-area-formacao/responsavel-area-formacao.model';
import { ResponsavelAreaFormacaoService } from 'app/entities/responsavel-area-formacao/service/responsavel-area-formacao.service';
import { IResponsavelCurso } from 'app/entities/responsavel-curso/responsavel-curso.model';
import { ResponsavelCursoService } from 'app/entities/responsavel-curso/service/responsavel-curso.service';
import { IResponsavelDisciplina } from 'app/entities/responsavel-disciplina/responsavel-disciplina.model';
import { ResponsavelDisciplinaService } from 'app/entities/responsavel-disciplina/service/responsavel-disciplina.service';
import { IResponsavelTurma } from 'app/entities/responsavel-turma/responsavel-turma.model';
import { ResponsavelTurmaService } from 'app/entities/responsavel-turma/service/responsavel-turma.service';

import { AnoLectivoUpdateComponent } from './ano-lectivo-update.component';

describe('AnoLectivo Management Update Component', () => {
  let comp: AnoLectivoUpdateComponent;
  let fixture: ComponentFixture<AnoLectivoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anoLectivoFormService: AnoLectivoFormService;
  let anoLectivoService: AnoLectivoService;
  let docenteService: DocenteService;
  let userService: UserService;
  let nivelEnsinoService: NivelEnsinoService;
  let turmaService: TurmaService;
  let horarioService: HorarioService;
  let planoAulaService: PlanoAulaService;
  let licaoService: LicaoService;
  let processoSelectivoMatriculaService: ProcessoSelectivoMatriculaService;
  let ocorrenciaService: OcorrenciaService;
  let notasPeriodicaDisciplinaService: NotasPeriodicaDisciplinaService;
  let notasGeralDisciplinaService: NotasGeralDisciplinaService;
  let dissertacaoFinalCursoService: DissertacaoFinalCursoService;
  let facturaService: FacturaService;
  let reciboService: ReciboService;
  let responsavelTurnoService: ResponsavelTurnoService;
  let responsavelAreaFormacaoService: ResponsavelAreaFormacaoService;
  let responsavelCursoService: ResponsavelCursoService;
  let responsavelDisciplinaService: ResponsavelDisciplinaService;
  let responsavelTurmaService: ResponsavelTurmaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AnoLectivoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AnoLectivoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnoLectivoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anoLectivoFormService = TestBed.inject(AnoLectivoFormService);
    anoLectivoService = TestBed.inject(AnoLectivoService);
    docenteService = TestBed.inject(DocenteService);
    userService = TestBed.inject(UserService);
    nivelEnsinoService = TestBed.inject(NivelEnsinoService);
    turmaService = TestBed.inject(TurmaService);
    horarioService = TestBed.inject(HorarioService);
    planoAulaService = TestBed.inject(PlanoAulaService);
    licaoService = TestBed.inject(LicaoService);
    processoSelectivoMatriculaService = TestBed.inject(ProcessoSelectivoMatriculaService);
    ocorrenciaService = TestBed.inject(OcorrenciaService);
    notasPeriodicaDisciplinaService = TestBed.inject(NotasPeriodicaDisciplinaService);
    notasGeralDisciplinaService = TestBed.inject(NotasGeralDisciplinaService);
    dissertacaoFinalCursoService = TestBed.inject(DissertacaoFinalCursoService);
    facturaService = TestBed.inject(FacturaService);
    reciboService = TestBed.inject(ReciboService);
    responsavelTurnoService = TestBed.inject(ResponsavelTurnoService);
    responsavelAreaFormacaoService = TestBed.inject(ResponsavelAreaFormacaoService);
    responsavelCursoService = TestBed.inject(ResponsavelCursoService);
    responsavelDisciplinaService = TestBed.inject(ResponsavelDisciplinaService);
    responsavelTurmaService = TestBed.inject(ResponsavelTurmaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Docente query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const directorGeral: IDocente = { id: 64883 };
      anoLectivo.directorGeral = directorGeral;
      const subDirectorPdagogico: IDocente = { id: 90631 };
      anoLectivo.subDirectorPdagogico = subDirectorPdagogico;
      const subDirectorAdministrativo: IDocente = { id: 87370 };
      anoLectivo.subDirectorAdministrativo = subDirectorAdministrativo;
      const responsavelSecretariaGeral: IDocente = { id: 19224 };
      anoLectivo.responsavelSecretariaGeral = responsavelSecretariaGeral;
      const responsavelSecretariaPedagogico: IDocente = { id: 21209 };
      anoLectivo.responsavelSecretariaPedagogico = responsavelSecretariaPedagogico;

      const docenteCollection: IDocente[] = [{ id: 32016 }];
      jest.spyOn(docenteService, 'query').mockReturnValue(of(new HttpResponse({ body: docenteCollection })));
      const additionalDocentes = [
        directorGeral,
        subDirectorPdagogico,
        subDirectorAdministrativo,
        responsavelSecretariaGeral,
        responsavelSecretariaPedagogico,
      ];
      const expectedCollection: IDocente[] = [...additionalDocentes, ...docenteCollection];
      jest.spyOn(docenteService, 'addDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(docenteService.query).toHaveBeenCalled();
      expect(docenteService.addDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        docenteCollection,
        ...additionalDocentes.map(expect.objectContaining)
      );
      expect(comp.docentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const utilizador: IUser = { id: 31950 };
      anoLectivo.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 7038 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NivelEnsino query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const nivesEnsinos: INivelEnsino[] = [{ id: 78957 }];
      anoLectivo.nivesEnsinos = nivesEnsinos;

      const nivelEnsinoCollection: INivelEnsino[] = [{ id: 41550 }];
      jest.spyOn(nivelEnsinoService, 'query').mockReturnValue(of(new HttpResponse({ body: nivelEnsinoCollection })));
      const additionalNivelEnsinos = [...nivesEnsinos];
      const expectedCollection: INivelEnsino[] = [...additionalNivelEnsinos, ...nivelEnsinoCollection];
      jest.spyOn(nivelEnsinoService, 'addNivelEnsinoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(nivelEnsinoService.query).toHaveBeenCalled();
      expect(nivelEnsinoService.addNivelEnsinoToCollectionIfMissing).toHaveBeenCalledWith(
        nivelEnsinoCollection,
        ...additionalNivelEnsinos.map(expect.objectContaining)
      );
      expect(comp.nivelEnsinosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turma query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const turma: ITurma = { id: 49946 };
      anoLectivo.turma = turma;

      const turmaCollection: ITurma[] = [{ id: 60350 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [turma];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Horario query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const horario: IHorario = { id: 73026 };
      anoLectivo.horario = horario;

      const horarioCollection: IHorario[] = [{ id: 18147 }];
      jest.spyOn(horarioService, 'query').mockReturnValue(of(new HttpResponse({ body: horarioCollection })));
      const additionalHorarios = [horario];
      const expectedCollection: IHorario[] = [...additionalHorarios, ...horarioCollection];
      jest.spyOn(horarioService, 'addHorarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(horarioService.query).toHaveBeenCalled();
      expect(horarioService.addHorarioToCollectionIfMissing).toHaveBeenCalledWith(
        horarioCollection,
        ...additionalHorarios.map(expect.objectContaining)
      );
      expect(comp.horariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoAula query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const planoAula: IPlanoAula = { id: 71617 };
      anoLectivo.planoAula = planoAula;

      const planoAulaCollection: IPlanoAula[] = [{ id: 63710 }];
      jest.spyOn(planoAulaService, 'query').mockReturnValue(of(new HttpResponse({ body: planoAulaCollection })));
      const additionalPlanoAulas = [planoAula];
      const expectedCollection: IPlanoAula[] = [...additionalPlanoAulas, ...planoAulaCollection];
      jest.spyOn(planoAulaService, 'addPlanoAulaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(planoAulaService.query).toHaveBeenCalled();
      expect(planoAulaService.addPlanoAulaToCollectionIfMissing).toHaveBeenCalledWith(
        planoAulaCollection,
        ...additionalPlanoAulas.map(expect.objectContaining)
      );
      expect(comp.planoAulasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Licao query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const licao: ILicao = { id: 81421 };
      anoLectivo.licao = licao;

      const licaoCollection: ILicao[] = [{ id: 3069 }];
      jest.spyOn(licaoService, 'query').mockReturnValue(of(new HttpResponse({ body: licaoCollection })));
      const additionalLicaos = [licao];
      const expectedCollection: ILicao[] = [...additionalLicaos, ...licaoCollection];
      jest.spyOn(licaoService, 'addLicaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(licaoService.query).toHaveBeenCalled();
      expect(licaoService.addLicaoToCollectionIfMissing).toHaveBeenCalledWith(
        licaoCollection,
        ...additionalLicaos.map(expect.objectContaining)
      );
      expect(comp.licaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProcessoSelectivoMatricula query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const processoSelectivoMatricula: IProcessoSelectivoMatricula = { id: 71390 };
      anoLectivo.processoSelectivoMatricula = processoSelectivoMatricula;

      const processoSelectivoMatriculaCollection: IProcessoSelectivoMatricula[] = [{ id: 19617 }];
      jest
        .spyOn(processoSelectivoMatriculaService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: processoSelectivoMatriculaCollection })));
      const additionalProcessoSelectivoMatriculas = [processoSelectivoMatricula];
      const expectedCollection: IProcessoSelectivoMatricula[] = [
        ...additionalProcessoSelectivoMatriculas,
        ...processoSelectivoMatriculaCollection,
      ];
      jest
        .spyOn(processoSelectivoMatriculaService, 'addProcessoSelectivoMatriculaToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(processoSelectivoMatriculaService.query).toHaveBeenCalled();
      expect(processoSelectivoMatriculaService.addProcessoSelectivoMatriculaToCollectionIfMissing).toHaveBeenCalledWith(
        processoSelectivoMatriculaCollection,
        ...additionalProcessoSelectivoMatriculas.map(expect.objectContaining)
      );
      expect(comp.processoSelectivoMatriculasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ocorrencia query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const ocorrencia: IOcorrencia = { id: 50347 };
      anoLectivo.ocorrencia = ocorrencia;

      const ocorrenciaCollection: IOcorrencia[] = [{ id: 26175 }];
      jest.spyOn(ocorrenciaService, 'query').mockReturnValue(of(new HttpResponse({ body: ocorrenciaCollection })));
      const additionalOcorrencias = [ocorrencia];
      const expectedCollection: IOcorrencia[] = [...additionalOcorrencias, ...ocorrenciaCollection];
      jest.spyOn(ocorrenciaService, 'addOcorrenciaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(ocorrenciaService.query).toHaveBeenCalled();
      expect(ocorrenciaService.addOcorrenciaToCollectionIfMissing).toHaveBeenCalledWith(
        ocorrenciaCollection,
        ...additionalOcorrencias.map(expect.objectContaining)
      );
      expect(comp.ocorrenciasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NotasPeriodicaDisciplina query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const notasPeriodicaDisciplina: INotasPeriodicaDisciplina = { id: 60555 };
      anoLectivo.notasPeriodicaDisciplina = notasPeriodicaDisciplina;

      const notasPeriodicaDisciplinaCollection: INotasPeriodicaDisciplina[] = [{ id: 42414 }];
      jest
        .spyOn(notasPeriodicaDisciplinaService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: notasPeriodicaDisciplinaCollection })));
      const additionalNotasPeriodicaDisciplinas = [notasPeriodicaDisciplina];
      const expectedCollection: INotasPeriodicaDisciplina[] = [
        ...additionalNotasPeriodicaDisciplinas,
        ...notasPeriodicaDisciplinaCollection,
      ];
      jest.spyOn(notasPeriodicaDisciplinaService, 'addNotasPeriodicaDisciplinaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(notasPeriodicaDisciplinaService.query).toHaveBeenCalled();
      expect(notasPeriodicaDisciplinaService.addNotasPeriodicaDisciplinaToCollectionIfMissing).toHaveBeenCalledWith(
        notasPeriodicaDisciplinaCollection,
        ...additionalNotasPeriodicaDisciplinas.map(expect.objectContaining)
      );
      expect(comp.notasPeriodicaDisciplinasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NotasGeralDisciplina query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const notasGeralDisciplina: INotasGeralDisciplina = { id: 2282 };
      anoLectivo.notasGeralDisciplina = notasGeralDisciplina;

      const notasGeralDisciplinaCollection: INotasGeralDisciplina[] = [{ id: 82175 }];
      jest.spyOn(notasGeralDisciplinaService, 'query').mockReturnValue(of(new HttpResponse({ body: notasGeralDisciplinaCollection })));
      const additionalNotasGeralDisciplinas = [notasGeralDisciplina];
      const expectedCollection: INotasGeralDisciplina[] = [...additionalNotasGeralDisciplinas, ...notasGeralDisciplinaCollection];
      jest.spyOn(notasGeralDisciplinaService, 'addNotasGeralDisciplinaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(notasGeralDisciplinaService.query).toHaveBeenCalled();
      expect(notasGeralDisciplinaService.addNotasGeralDisciplinaToCollectionIfMissing).toHaveBeenCalledWith(
        notasGeralDisciplinaCollection,
        ...additionalNotasGeralDisciplinas.map(expect.objectContaining)
      );
      expect(comp.notasGeralDisciplinasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DissertacaoFinalCurso query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 13402 };
      anoLectivo.dissertacaoFinalCurso = dissertacaoFinalCurso;

      const dissertacaoFinalCursoCollection: IDissertacaoFinalCurso[] = [{ id: 13094 }];
      jest.spyOn(dissertacaoFinalCursoService, 'query').mockReturnValue(of(new HttpResponse({ body: dissertacaoFinalCursoCollection })));
      const additionalDissertacaoFinalCursos = [dissertacaoFinalCurso];
      const expectedCollection: IDissertacaoFinalCurso[] = [...additionalDissertacaoFinalCursos, ...dissertacaoFinalCursoCollection];
      jest.spyOn(dissertacaoFinalCursoService, 'addDissertacaoFinalCursoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(dissertacaoFinalCursoService.query).toHaveBeenCalled();
      expect(dissertacaoFinalCursoService.addDissertacaoFinalCursoToCollectionIfMissing).toHaveBeenCalledWith(
        dissertacaoFinalCursoCollection,
        ...additionalDissertacaoFinalCursos.map(expect.objectContaining)
      );
      expect(comp.dissertacaoFinalCursosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Factura query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const factura: IFactura = { id: 26536 };
      anoLectivo.factura = factura;

      const facturaCollection: IFactura[] = [{ id: 91919 }];
      jest.spyOn(facturaService, 'query').mockReturnValue(of(new HttpResponse({ body: facturaCollection })));
      const additionalFacturas = [factura];
      const expectedCollection: IFactura[] = [...additionalFacturas, ...facturaCollection];
      jest.spyOn(facturaService, 'addFacturaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(facturaService.query).toHaveBeenCalled();
      expect(facturaService.addFacturaToCollectionIfMissing).toHaveBeenCalledWith(
        facturaCollection,
        ...additionalFacturas.map(expect.objectContaining)
      );
      expect(comp.facturasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Recibo query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const recibo: IRecibo = { id: 8596 };
      anoLectivo.recibo = recibo;

      const reciboCollection: IRecibo[] = [{ id: 76641 }];
      jest.spyOn(reciboService, 'query').mockReturnValue(of(new HttpResponse({ body: reciboCollection })));
      const additionalRecibos = [recibo];
      const expectedCollection: IRecibo[] = [...additionalRecibos, ...reciboCollection];
      jest.spyOn(reciboService, 'addReciboToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(reciboService.query).toHaveBeenCalled();
      expect(reciboService.addReciboToCollectionIfMissing).toHaveBeenCalledWith(
        reciboCollection,
        ...additionalRecibos.map(expect.objectContaining)
      );
      expect(comp.recibosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelTurno query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const responsavelTurno: IResponsavelTurno = { id: 82975 };
      anoLectivo.responsavelTurno = responsavelTurno;

      const responsavelTurnoCollection: IResponsavelTurno[] = [{ id: 44425 }];
      jest.spyOn(responsavelTurnoService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelTurnoCollection })));
      const additionalResponsavelTurnos = [responsavelTurno];
      const expectedCollection: IResponsavelTurno[] = [...additionalResponsavelTurnos, ...responsavelTurnoCollection];
      jest.spyOn(responsavelTurnoService, 'addResponsavelTurnoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(responsavelTurnoService.query).toHaveBeenCalled();
      expect(responsavelTurnoService.addResponsavelTurnoToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelTurnoCollection,
        ...additionalResponsavelTurnos.map(expect.objectContaining)
      );
      expect(comp.responsavelTurnosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelAreaFormacao query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const responsavelAreaFormacao: IResponsavelAreaFormacao = { id: 61921 };
      anoLectivo.responsavelAreaFormacao = responsavelAreaFormacao;

      const responsavelAreaFormacaoCollection: IResponsavelAreaFormacao[] = [{ id: 16587 }];
      jest
        .spyOn(responsavelAreaFormacaoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: responsavelAreaFormacaoCollection })));
      const additionalResponsavelAreaFormacaos = [responsavelAreaFormacao];
      const expectedCollection: IResponsavelAreaFormacao[] = [...additionalResponsavelAreaFormacaos, ...responsavelAreaFormacaoCollection];
      jest.spyOn(responsavelAreaFormacaoService, 'addResponsavelAreaFormacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(responsavelAreaFormacaoService.query).toHaveBeenCalled();
      expect(responsavelAreaFormacaoService.addResponsavelAreaFormacaoToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelAreaFormacaoCollection,
        ...additionalResponsavelAreaFormacaos.map(expect.objectContaining)
      );
      expect(comp.responsavelAreaFormacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelCurso query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const responsavelCurso: IResponsavelCurso = { id: 32642 };
      anoLectivo.responsavelCurso = responsavelCurso;

      const responsavelCursoCollection: IResponsavelCurso[] = [{ id: 35558 }];
      jest.spyOn(responsavelCursoService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelCursoCollection })));
      const additionalResponsavelCursos = [responsavelCurso];
      const expectedCollection: IResponsavelCurso[] = [...additionalResponsavelCursos, ...responsavelCursoCollection];
      jest.spyOn(responsavelCursoService, 'addResponsavelCursoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(responsavelCursoService.query).toHaveBeenCalled();
      expect(responsavelCursoService.addResponsavelCursoToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelCursoCollection,
        ...additionalResponsavelCursos.map(expect.objectContaining)
      );
      expect(comp.responsavelCursosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelDisciplina query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const responsavelDisciplina: IResponsavelDisciplina = { id: 87516 };
      anoLectivo.responsavelDisciplina = responsavelDisciplina;

      const responsavelDisciplinaCollection: IResponsavelDisciplina[] = [{ id: 81634 }];
      jest.spyOn(responsavelDisciplinaService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelDisciplinaCollection })));
      const additionalResponsavelDisciplinas = [responsavelDisciplina];
      const expectedCollection: IResponsavelDisciplina[] = [...additionalResponsavelDisciplinas, ...responsavelDisciplinaCollection];
      jest.spyOn(responsavelDisciplinaService, 'addResponsavelDisciplinaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(responsavelDisciplinaService.query).toHaveBeenCalled();
      expect(responsavelDisciplinaService.addResponsavelDisciplinaToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelDisciplinaCollection,
        ...additionalResponsavelDisciplinas.map(expect.objectContaining)
      );
      expect(comp.responsavelDisciplinasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelTurma query and add missing value', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const responsavelTurma: IResponsavelTurma = { id: 58375 };
      anoLectivo.responsavelTurma = responsavelTurma;

      const responsavelTurmaCollection: IResponsavelTurma[] = [{ id: 49550 }];
      jest.spyOn(responsavelTurmaService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelTurmaCollection })));
      const additionalResponsavelTurmas = [responsavelTurma];
      const expectedCollection: IResponsavelTurma[] = [...additionalResponsavelTurmas, ...responsavelTurmaCollection];
      jest.spyOn(responsavelTurmaService, 'addResponsavelTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(responsavelTurmaService.query).toHaveBeenCalled();
      expect(responsavelTurmaService.addResponsavelTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelTurmaCollection,
        ...additionalResponsavelTurmas.map(expect.objectContaining)
      );
      expect(comp.responsavelTurmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anoLectivo: IAnoLectivo = { id: 456 };
      const directorGeral: IDocente = { id: 8449 };
      anoLectivo.directorGeral = directorGeral;
      const subDirectorPdagogico: IDocente = { id: 59516 };
      anoLectivo.subDirectorPdagogico = subDirectorPdagogico;
      const subDirectorAdministrativo: IDocente = { id: 94532 };
      anoLectivo.subDirectorAdministrativo = subDirectorAdministrativo;
      const responsavelSecretariaGeral: IDocente = { id: 79996 };
      anoLectivo.responsavelSecretariaGeral = responsavelSecretariaGeral;
      const responsavelSecretariaPedagogico: IDocente = { id: 72551 };
      anoLectivo.responsavelSecretariaPedagogico = responsavelSecretariaPedagogico;
      const utilizador: IUser = { id: 31285 };
      anoLectivo.utilizador = utilizador;
      const nivesEnsino: INivelEnsino = { id: 71551 };
      anoLectivo.nivesEnsinos = [nivesEnsino];
      const turma: ITurma = { id: 88069 };
      anoLectivo.turma = turma;
      const horario: IHorario = { id: 41372 };
      anoLectivo.horario = horario;
      const planoAula: IPlanoAula = { id: 17625 };
      anoLectivo.planoAula = planoAula;
      const licao: ILicao = { id: 84331 };
      anoLectivo.licao = licao;
      const processoSelectivoMatricula: IProcessoSelectivoMatricula = { id: 75384 };
      anoLectivo.processoSelectivoMatricula = processoSelectivoMatricula;
      const ocorrencia: IOcorrencia = { id: 93218 };
      anoLectivo.ocorrencia = ocorrencia;
      const notasPeriodicaDisciplina: INotasPeriodicaDisciplina = { id: 18011 };
      anoLectivo.notasPeriodicaDisciplina = notasPeriodicaDisciplina;
      const notasGeralDisciplina: INotasGeralDisciplina = { id: 84052 };
      anoLectivo.notasGeralDisciplina = notasGeralDisciplina;
      const dissertacaoFinalCurso: IDissertacaoFinalCurso = { id: 3886 };
      anoLectivo.dissertacaoFinalCurso = dissertacaoFinalCurso;
      const factura: IFactura = { id: 68307 };
      anoLectivo.factura = factura;
      const recibo: IRecibo = { id: 38103 };
      anoLectivo.recibo = recibo;
      const responsavelTurno: IResponsavelTurno = { id: 88586 };
      anoLectivo.responsavelTurno = responsavelTurno;
      const responsavelAreaFormacao: IResponsavelAreaFormacao = { id: 1981 };
      anoLectivo.responsavelAreaFormacao = responsavelAreaFormacao;
      const responsavelCurso: IResponsavelCurso = { id: 43367 };
      anoLectivo.responsavelCurso = responsavelCurso;
      const responsavelDisciplina: IResponsavelDisciplina = { id: 37753 };
      anoLectivo.responsavelDisciplina = responsavelDisciplina;
      const responsavelTurma: IResponsavelTurma = { id: 17448 };
      anoLectivo.responsavelTurma = responsavelTurma;

      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      expect(comp.docentesSharedCollection).toContain(directorGeral);
      expect(comp.docentesSharedCollection).toContain(subDirectorPdagogico);
      expect(comp.docentesSharedCollection).toContain(subDirectorAdministrativo);
      expect(comp.docentesSharedCollection).toContain(responsavelSecretariaGeral);
      expect(comp.docentesSharedCollection).toContain(responsavelSecretariaPedagogico);
      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.nivelEnsinosSharedCollection).toContain(nivesEnsino);
      expect(comp.turmasSharedCollection).toContain(turma);
      expect(comp.horariosSharedCollection).toContain(horario);
      expect(comp.planoAulasSharedCollection).toContain(planoAula);
      expect(comp.licaosSharedCollection).toContain(licao);
      expect(comp.processoSelectivoMatriculasSharedCollection).toContain(processoSelectivoMatricula);
      expect(comp.ocorrenciasSharedCollection).toContain(ocorrencia);
      expect(comp.notasPeriodicaDisciplinasSharedCollection).toContain(notasPeriodicaDisciplina);
      expect(comp.notasGeralDisciplinasSharedCollection).toContain(notasGeralDisciplina);
      expect(comp.dissertacaoFinalCursosSharedCollection).toContain(dissertacaoFinalCurso);
      expect(comp.facturasSharedCollection).toContain(factura);
      expect(comp.recibosSharedCollection).toContain(recibo);
      expect(comp.responsavelTurnosSharedCollection).toContain(responsavelTurno);
      expect(comp.responsavelAreaFormacaosSharedCollection).toContain(responsavelAreaFormacao);
      expect(comp.responsavelCursosSharedCollection).toContain(responsavelCurso);
      expect(comp.responsavelDisciplinasSharedCollection).toContain(responsavelDisciplina);
      expect(comp.responsavelTurmasSharedCollection).toContain(responsavelTurma);
      expect(comp.anoLectivo).toEqual(anoLectivo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnoLectivo>>();
      const anoLectivo = { id: 123 };
      jest.spyOn(anoLectivoFormService, 'getAnoLectivo').mockReturnValue(anoLectivo);
      jest.spyOn(anoLectivoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anoLectivo }));
      saveSubject.complete();

      // THEN
      expect(anoLectivoFormService.getAnoLectivo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anoLectivoService.update).toHaveBeenCalledWith(expect.objectContaining(anoLectivo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnoLectivo>>();
      const anoLectivo = { id: 123 };
      jest.spyOn(anoLectivoFormService, 'getAnoLectivo').mockReturnValue({ id: null });
      jest.spyOn(anoLectivoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anoLectivo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anoLectivo }));
      saveSubject.complete();

      // THEN
      expect(anoLectivoFormService.getAnoLectivo).toHaveBeenCalled();
      expect(anoLectivoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnoLectivo>>();
      const anoLectivo = { id: 123 };
      jest.spyOn(anoLectivoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anoLectivo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anoLectivoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDocente', () => {
      it('Should forward to docenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(docenteService, 'compareDocente');
        comp.compareDocente(entity, entity2);
        expect(docenteService.compareDocente).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNivelEnsino', () => {
      it('Should forward to nivelEnsinoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(nivelEnsinoService, 'compareNivelEnsino');
        comp.compareNivelEnsino(entity, entity2);
        expect(nivelEnsinoService.compareNivelEnsino).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTurma', () => {
      it('Should forward to turmaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(turmaService, 'compareTurma');
        comp.compareTurma(entity, entity2);
        expect(turmaService.compareTurma).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareHorario', () => {
      it('Should forward to horarioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(horarioService, 'compareHorario');
        comp.compareHorario(entity, entity2);
        expect(horarioService.compareHorario).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoAula', () => {
      it('Should forward to planoAulaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoAulaService, 'comparePlanoAula');
        comp.comparePlanoAula(entity, entity2);
        expect(planoAulaService.comparePlanoAula).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLicao', () => {
      it('Should forward to licaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(licaoService, 'compareLicao');
        comp.compareLicao(entity, entity2);
        expect(licaoService.compareLicao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProcessoSelectivoMatricula', () => {
      it('Should forward to processoSelectivoMatriculaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(processoSelectivoMatriculaService, 'compareProcessoSelectivoMatricula');
        comp.compareProcessoSelectivoMatricula(entity, entity2);
        expect(processoSelectivoMatriculaService.compareProcessoSelectivoMatricula).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOcorrencia', () => {
      it('Should forward to ocorrenciaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ocorrenciaService, 'compareOcorrencia');
        comp.compareOcorrencia(entity, entity2);
        expect(ocorrenciaService.compareOcorrencia).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNotasPeriodicaDisciplina', () => {
      it('Should forward to notasPeriodicaDisciplinaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(notasPeriodicaDisciplinaService, 'compareNotasPeriodicaDisciplina');
        comp.compareNotasPeriodicaDisciplina(entity, entity2);
        expect(notasPeriodicaDisciplinaService.compareNotasPeriodicaDisciplina).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNotasGeralDisciplina', () => {
      it('Should forward to notasGeralDisciplinaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(notasGeralDisciplinaService, 'compareNotasGeralDisciplina');
        comp.compareNotasGeralDisciplina(entity, entity2);
        expect(notasGeralDisciplinaService.compareNotasGeralDisciplina).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDissertacaoFinalCurso', () => {
      it('Should forward to dissertacaoFinalCursoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dissertacaoFinalCursoService, 'compareDissertacaoFinalCurso');
        comp.compareDissertacaoFinalCurso(entity, entity2);
        expect(dissertacaoFinalCursoService.compareDissertacaoFinalCurso).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFactura', () => {
      it('Should forward to facturaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(facturaService, 'compareFactura');
        comp.compareFactura(entity, entity2);
        expect(facturaService.compareFactura).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRecibo', () => {
      it('Should forward to reciboService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(reciboService, 'compareRecibo');
        comp.compareRecibo(entity, entity2);
        expect(reciboService.compareRecibo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResponsavelTurno', () => {
      it('Should forward to responsavelTurnoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelTurnoService, 'compareResponsavelTurno');
        comp.compareResponsavelTurno(entity, entity2);
        expect(responsavelTurnoService.compareResponsavelTurno).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResponsavelAreaFormacao', () => {
      it('Should forward to responsavelAreaFormacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelAreaFormacaoService, 'compareResponsavelAreaFormacao');
        comp.compareResponsavelAreaFormacao(entity, entity2);
        expect(responsavelAreaFormacaoService.compareResponsavelAreaFormacao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResponsavelCurso', () => {
      it('Should forward to responsavelCursoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelCursoService, 'compareResponsavelCurso');
        comp.compareResponsavelCurso(entity, entity2);
        expect(responsavelCursoService.compareResponsavelCurso).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResponsavelDisciplina', () => {
      it('Should forward to responsavelDisciplinaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelDisciplinaService, 'compareResponsavelDisciplina');
        comp.compareResponsavelDisciplina(entity, entity2);
        expect(responsavelDisciplinaService.compareResponsavelDisciplina).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResponsavelTurma', () => {
      it('Should forward to responsavelTurmaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelTurmaService, 'compareResponsavelTurma');
        comp.compareResponsavelTurma(entity, entity2);
        expect(responsavelTurmaService.compareResponsavelTurma).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
