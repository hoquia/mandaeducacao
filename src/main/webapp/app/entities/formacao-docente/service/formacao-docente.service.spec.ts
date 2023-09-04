import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFormacaoDocente } from '../formacao-docente.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../formacao-docente.test-samples';

import { FormacaoDocenteService, RestFormacaoDocente } from './formacao-docente.service';

const requireRestSample: RestFormacaoDocente = {
  ...sampleWithRequiredData,
  inicio: sampleWithRequiredData.inicio?.format(DATE_FORMAT),
  fim: sampleWithRequiredData.fim?.format(DATE_FORMAT),
};

describe('FormacaoDocente Service', () => {
  let service: FormacaoDocenteService;
  let httpMock: HttpTestingController;
  let expectedResult: IFormacaoDocente | IFormacaoDocente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FormacaoDocenteService);
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

    it('should create a FormacaoDocente', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const formacaoDocente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(formacaoDocente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FormacaoDocente', () => {
      const formacaoDocente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(formacaoDocente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FormacaoDocente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FormacaoDocente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FormacaoDocente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFormacaoDocenteToCollectionIfMissing', () => {
      it('should add a FormacaoDocente to an empty array', () => {
        const formacaoDocente: IFormacaoDocente = sampleWithRequiredData;
        expectedResult = service.addFormacaoDocenteToCollectionIfMissing([], formacaoDocente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formacaoDocente);
      });

      it('should not add a FormacaoDocente to an array that contains it', () => {
        const formacaoDocente: IFormacaoDocente = sampleWithRequiredData;
        const formacaoDocenteCollection: IFormacaoDocente[] = [
          {
            ...formacaoDocente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFormacaoDocenteToCollectionIfMissing(formacaoDocenteCollection, formacaoDocente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FormacaoDocente to an array that doesn't contain it", () => {
        const formacaoDocente: IFormacaoDocente = sampleWithRequiredData;
        const formacaoDocenteCollection: IFormacaoDocente[] = [sampleWithPartialData];
        expectedResult = service.addFormacaoDocenteToCollectionIfMissing(formacaoDocenteCollection, formacaoDocente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formacaoDocente);
      });

      it('should add only unique FormacaoDocente to an array', () => {
        const formacaoDocenteArray: IFormacaoDocente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const formacaoDocenteCollection: IFormacaoDocente[] = [sampleWithRequiredData];
        expectedResult = service.addFormacaoDocenteToCollectionIfMissing(formacaoDocenteCollection, ...formacaoDocenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const formacaoDocente: IFormacaoDocente = sampleWithRequiredData;
        const formacaoDocente2: IFormacaoDocente = sampleWithPartialData;
        expectedResult = service.addFormacaoDocenteToCollectionIfMissing([], formacaoDocente, formacaoDocente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(formacaoDocente);
        expect(expectedResult).toContain(formacaoDocente2);
      });

      it('should accept null and undefined values', () => {
        const formacaoDocente: IFormacaoDocente = sampleWithRequiredData;
        expectedResult = service.addFormacaoDocenteToCollectionIfMissing([], null, formacaoDocente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(formacaoDocente);
      });

      it('should return initial array if no FormacaoDocente is added', () => {
        const formacaoDocenteCollection: IFormacaoDocente[] = [sampleWithRequiredData];
        expectedResult = service.addFormacaoDocenteToCollectionIfMissing(formacaoDocenteCollection, undefined, null);
        expect(expectedResult).toEqual(formacaoDocenteCollection);
      });
    });

    describe('compareFormacaoDocente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFormacaoDocente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFormacaoDocente(entity1, entity2);
        const compareResult2 = service.compareFormacaoDocente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFormacaoDocente(entity1, entity2);
        const compareResult2 = service.compareFormacaoDocente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFormacaoDocente(entity1, entity2);
        const compareResult2 = service.compareFormacaoDocente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
