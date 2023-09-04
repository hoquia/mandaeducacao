import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDocente } from '../docente.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../docente.test-samples';

import { DocenteService, RestDocente } from './docente.service';

const requireRestSample: RestDocente = {
  ...sampleWithRequiredData,
  nascimento: sampleWithRequiredData.nascimento?.format(DATE_FORMAT),
  documentoEmissao: sampleWithRequiredData.documentoEmissao?.format(DATE_FORMAT),
  documentoValidade: sampleWithRequiredData.documentoValidade?.format(DATE_FORMAT),
  dataInicioFuncoes: sampleWithRequiredData.dataInicioFuncoes?.format(DATE_FORMAT),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('Docente Service', () => {
  let service: DocenteService;
  let httpMock: HttpTestingController;
  let expectedResult: IDocente | IDocente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DocenteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Docente', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const docente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(docente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Docente', () => {
      const docente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(docente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Docente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Docente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Docente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDocenteToCollectionIfMissing', () => {
      it('should add a Docente to an empty array', () => {
        const docente: IDocente = sampleWithRequiredData;
        expectedResult = service.addDocenteToCollectionIfMissing([], docente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(docente);
      });

      it('should not add a Docente to an array that contains it', () => {
        const docente: IDocente = sampleWithRequiredData;
        const docenteCollection: IDocente[] = [
          {
            ...docente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDocenteToCollectionIfMissing(docenteCollection, docente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Docente to an array that doesn't contain it", () => {
        const docente: IDocente = sampleWithRequiredData;
        const docenteCollection: IDocente[] = [sampleWithPartialData];
        expectedResult = service.addDocenteToCollectionIfMissing(docenteCollection, docente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(docente);
      });

      it('should add only unique Docente to an array', () => {
        const docenteArray: IDocente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const docenteCollection: IDocente[] = [sampleWithRequiredData];
        expectedResult = service.addDocenteToCollectionIfMissing(docenteCollection, ...docenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const docente: IDocente = sampleWithRequiredData;
        const docente2: IDocente = sampleWithPartialData;
        expectedResult = service.addDocenteToCollectionIfMissing([], docente, docente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(docente);
        expect(expectedResult).toContain(docente2);
      });

      it('should accept null and undefined values', () => {
        const docente: IDocente = sampleWithRequiredData;
        expectedResult = service.addDocenteToCollectionIfMissing([], null, docente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(docente);
      });

      it('should return initial array if no Docente is added', () => {
        const docenteCollection: IDocente[] = [sampleWithRequiredData];
        expectedResult = service.addDocenteToCollectionIfMissing(docenteCollection, undefined, null);
        expect(expectedResult).toEqual(docenteCollection);
      });
    });

    describe('compareDocente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDocente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDocente(entity1, entity2);
        const compareResult2 = service.compareDocente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDocente(entity1, entity2);
        const compareResult2 = service.compareDocente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDocente(entity1, entity2);
        const compareResult2 = service.compareDocente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
