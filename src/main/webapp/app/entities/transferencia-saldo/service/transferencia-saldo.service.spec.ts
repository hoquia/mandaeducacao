import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITransferenciaSaldo } from '../transferencia-saldo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../transferencia-saldo.test-samples';

import { TransferenciaSaldoService, RestTransferenciaSaldo } from './transferencia-saldo.service';

const requireRestSample: RestTransferenciaSaldo = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('TransferenciaSaldo Service', () => {
  let service: TransferenciaSaldoService;
  let httpMock: HttpTestingController;
  let expectedResult: ITransferenciaSaldo | ITransferenciaSaldo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransferenciaSaldoService);
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

    it('should create a TransferenciaSaldo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const transferenciaSaldo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(transferenciaSaldo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TransferenciaSaldo', () => {
      const transferenciaSaldo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(transferenciaSaldo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TransferenciaSaldo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TransferenciaSaldo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TransferenciaSaldo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTransferenciaSaldoToCollectionIfMissing', () => {
      it('should add a TransferenciaSaldo to an empty array', () => {
        const transferenciaSaldo: ITransferenciaSaldo = sampleWithRequiredData;
        expectedResult = service.addTransferenciaSaldoToCollectionIfMissing([], transferenciaSaldo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferenciaSaldo);
      });

      it('should not add a TransferenciaSaldo to an array that contains it', () => {
        const transferenciaSaldo: ITransferenciaSaldo = sampleWithRequiredData;
        const transferenciaSaldoCollection: ITransferenciaSaldo[] = [
          {
            ...transferenciaSaldo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTransferenciaSaldoToCollectionIfMissing(transferenciaSaldoCollection, transferenciaSaldo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TransferenciaSaldo to an array that doesn't contain it", () => {
        const transferenciaSaldo: ITransferenciaSaldo = sampleWithRequiredData;
        const transferenciaSaldoCollection: ITransferenciaSaldo[] = [sampleWithPartialData];
        expectedResult = service.addTransferenciaSaldoToCollectionIfMissing(transferenciaSaldoCollection, transferenciaSaldo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferenciaSaldo);
      });

      it('should add only unique TransferenciaSaldo to an array', () => {
        const transferenciaSaldoArray: ITransferenciaSaldo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const transferenciaSaldoCollection: ITransferenciaSaldo[] = [sampleWithRequiredData];
        expectedResult = service.addTransferenciaSaldoToCollectionIfMissing(transferenciaSaldoCollection, ...transferenciaSaldoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transferenciaSaldo: ITransferenciaSaldo = sampleWithRequiredData;
        const transferenciaSaldo2: ITransferenciaSaldo = sampleWithPartialData;
        expectedResult = service.addTransferenciaSaldoToCollectionIfMissing([], transferenciaSaldo, transferenciaSaldo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferenciaSaldo);
        expect(expectedResult).toContain(transferenciaSaldo2);
      });

      it('should accept null and undefined values', () => {
        const transferenciaSaldo: ITransferenciaSaldo = sampleWithRequiredData;
        expectedResult = service.addTransferenciaSaldoToCollectionIfMissing([], null, transferenciaSaldo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferenciaSaldo);
      });

      it('should return initial array if no TransferenciaSaldo is added', () => {
        const transferenciaSaldoCollection: ITransferenciaSaldo[] = [sampleWithRequiredData];
        expectedResult = service.addTransferenciaSaldoToCollectionIfMissing(transferenciaSaldoCollection, undefined, null);
        expect(expectedResult).toEqual(transferenciaSaldoCollection);
      });
    });

    describe('compareTransferenciaSaldo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTransferenciaSaldo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTransferenciaSaldo(entity1, entity2);
        const compareResult2 = service.compareTransferenciaSaldo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTransferenciaSaldo(entity1, entity2);
        const compareResult2 = service.compareTransferenciaSaldo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTransferenciaSaldo(entity1, entity2);
        const compareResult2 = service.compareTransferenciaSaldo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
