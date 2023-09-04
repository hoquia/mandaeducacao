import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMeioPagamento } from '../meio-pagamento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../meio-pagamento.test-samples';

import { MeioPagamentoService } from './meio-pagamento.service';

const requireRestSample: IMeioPagamento = {
  ...sampleWithRequiredData,
};

describe('MeioPagamento Service', () => {
  let service: MeioPagamentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IMeioPagamento | IMeioPagamento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MeioPagamentoService);
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

    it('should create a MeioPagamento', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const meioPagamento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(meioPagamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MeioPagamento', () => {
      const meioPagamento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(meioPagamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MeioPagamento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MeioPagamento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MeioPagamento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMeioPagamentoToCollectionIfMissing', () => {
      it('should add a MeioPagamento to an empty array', () => {
        const meioPagamento: IMeioPagamento = sampleWithRequiredData;
        expectedResult = service.addMeioPagamentoToCollectionIfMissing([], meioPagamento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(meioPagamento);
      });

      it('should not add a MeioPagamento to an array that contains it', () => {
        const meioPagamento: IMeioPagamento = sampleWithRequiredData;
        const meioPagamentoCollection: IMeioPagamento[] = [
          {
            ...meioPagamento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMeioPagamentoToCollectionIfMissing(meioPagamentoCollection, meioPagamento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MeioPagamento to an array that doesn't contain it", () => {
        const meioPagamento: IMeioPagamento = sampleWithRequiredData;
        const meioPagamentoCollection: IMeioPagamento[] = [sampleWithPartialData];
        expectedResult = service.addMeioPagamentoToCollectionIfMissing(meioPagamentoCollection, meioPagamento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(meioPagamento);
      });

      it('should add only unique MeioPagamento to an array', () => {
        const meioPagamentoArray: IMeioPagamento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const meioPagamentoCollection: IMeioPagamento[] = [sampleWithRequiredData];
        expectedResult = service.addMeioPagamentoToCollectionIfMissing(meioPagamentoCollection, ...meioPagamentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const meioPagamento: IMeioPagamento = sampleWithRequiredData;
        const meioPagamento2: IMeioPagamento = sampleWithPartialData;
        expectedResult = service.addMeioPagamentoToCollectionIfMissing([], meioPagamento, meioPagamento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(meioPagamento);
        expect(expectedResult).toContain(meioPagamento2);
      });

      it('should accept null and undefined values', () => {
        const meioPagamento: IMeioPagamento = sampleWithRequiredData;
        expectedResult = service.addMeioPagamentoToCollectionIfMissing([], null, meioPagamento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(meioPagamento);
      });

      it('should return initial array if no MeioPagamento is added', () => {
        const meioPagamentoCollection: IMeioPagamento[] = [sampleWithRequiredData];
        expectedResult = service.addMeioPagamentoToCollectionIfMissing(meioPagamentoCollection, undefined, null);
        expect(expectedResult).toEqual(meioPagamentoCollection);
      });
    });

    describe('compareMeioPagamento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMeioPagamento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMeioPagamento(entity1, entity2);
        const compareResult2 = service.compareMeioPagamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMeioPagamento(entity1, entity2);
        const compareResult2 = service.compareMeioPagamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMeioPagamento(entity1, entity2);
        const compareResult2 = service.compareMeioPagamento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
