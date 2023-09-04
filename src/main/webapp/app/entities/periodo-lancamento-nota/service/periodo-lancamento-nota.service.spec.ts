import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPeriodoLancamentoNota } from '../periodo-lancamento-nota.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../periodo-lancamento-nota.test-samples';

import { PeriodoLancamentoNotaService, RestPeriodoLancamentoNota } from './periodo-lancamento-nota.service';

const requireRestSample: RestPeriodoLancamentoNota = {
  ...sampleWithRequiredData,
  de: sampleWithRequiredData.de?.toJSON(),
  ate: sampleWithRequiredData.ate?.toJSON(),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('PeriodoLancamentoNota Service', () => {
  let service: PeriodoLancamentoNotaService;
  let httpMock: HttpTestingController;
  let expectedResult: IPeriodoLancamentoNota | IPeriodoLancamentoNota[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PeriodoLancamentoNotaService);
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

    it('should create a PeriodoLancamentoNota', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const periodoLancamentoNota = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(periodoLancamentoNota).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PeriodoLancamentoNota', () => {
      const periodoLancamentoNota = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(periodoLancamentoNota).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PeriodoLancamentoNota', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PeriodoLancamentoNota', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PeriodoLancamentoNota', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPeriodoLancamentoNotaToCollectionIfMissing', () => {
      it('should add a PeriodoLancamentoNota to an empty array', () => {
        const periodoLancamentoNota: IPeriodoLancamentoNota = sampleWithRequiredData;
        expectedResult = service.addPeriodoLancamentoNotaToCollectionIfMissing([], periodoLancamentoNota);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(periodoLancamentoNota);
      });

      it('should not add a PeriodoLancamentoNota to an array that contains it', () => {
        const periodoLancamentoNota: IPeriodoLancamentoNota = sampleWithRequiredData;
        const periodoLancamentoNotaCollection: IPeriodoLancamentoNota[] = [
          {
            ...periodoLancamentoNota,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPeriodoLancamentoNotaToCollectionIfMissing(periodoLancamentoNotaCollection, periodoLancamentoNota);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PeriodoLancamentoNota to an array that doesn't contain it", () => {
        const periodoLancamentoNota: IPeriodoLancamentoNota = sampleWithRequiredData;
        const periodoLancamentoNotaCollection: IPeriodoLancamentoNota[] = [sampleWithPartialData];
        expectedResult = service.addPeriodoLancamentoNotaToCollectionIfMissing(periodoLancamentoNotaCollection, periodoLancamentoNota);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(periodoLancamentoNota);
      });

      it('should add only unique PeriodoLancamentoNota to an array', () => {
        const periodoLancamentoNotaArray: IPeriodoLancamentoNota[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const periodoLancamentoNotaCollection: IPeriodoLancamentoNota[] = [sampleWithRequiredData];
        expectedResult = service.addPeriodoLancamentoNotaToCollectionIfMissing(
          periodoLancamentoNotaCollection,
          ...periodoLancamentoNotaArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const periodoLancamentoNota: IPeriodoLancamentoNota = sampleWithRequiredData;
        const periodoLancamentoNota2: IPeriodoLancamentoNota = sampleWithPartialData;
        expectedResult = service.addPeriodoLancamentoNotaToCollectionIfMissing([], periodoLancamentoNota, periodoLancamentoNota2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(periodoLancamentoNota);
        expect(expectedResult).toContain(periodoLancamentoNota2);
      });

      it('should accept null and undefined values', () => {
        const periodoLancamentoNota: IPeriodoLancamentoNota = sampleWithRequiredData;
        expectedResult = service.addPeriodoLancamentoNotaToCollectionIfMissing([], null, periodoLancamentoNota, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(periodoLancamentoNota);
      });

      it('should return initial array if no PeriodoLancamentoNota is added', () => {
        const periodoLancamentoNotaCollection: IPeriodoLancamentoNota[] = [sampleWithRequiredData];
        expectedResult = service.addPeriodoLancamentoNotaToCollectionIfMissing(periodoLancamentoNotaCollection, undefined, null);
        expect(expectedResult).toEqual(periodoLancamentoNotaCollection);
      });
    });

    describe('comparePeriodoLancamentoNota', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePeriodoLancamentoNota(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePeriodoLancamentoNota(entity1, entity2);
        const compareResult2 = service.comparePeriodoLancamentoNota(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePeriodoLancamentoNota(entity1, entity2);
        const compareResult2 = service.comparePeriodoLancamentoNota(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePeriodoLancamentoNota(entity1, entity2);
        const compareResult2 = service.comparePeriodoLancamentoNota(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
