import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITransferenciaTurma } from '../transferencia-turma.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../transferencia-turma.test-samples';

import { TransferenciaTurmaService, RestTransferenciaTurma } from './transferencia-turma.service';

const requireRestSample: RestTransferenciaTurma = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('TransferenciaTurma Service', () => {
  let service: TransferenciaTurmaService;
  let httpMock: HttpTestingController;
  let expectedResult: ITransferenciaTurma | ITransferenciaTurma[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransferenciaTurmaService);
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

    it('should create a TransferenciaTurma', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const transferenciaTurma = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(transferenciaTurma).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TransferenciaTurma', () => {
      const transferenciaTurma = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(transferenciaTurma).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TransferenciaTurma', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TransferenciaTurma', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TransferenciaTurma', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTransferenciaTurmaToCollectionIfMissing', () => {
      it('should add a TransferenciaTurma to an empty array', () => {
        const transferenciaTurma: ITransferenciaTurma = sampleWithRequiredData;
        expectedResult = service.addTransferenciaTurmaToCollectionIfMissing([], transferenciaTurma);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferenciaTurma);
      });

      it('should not add a TransferenciaTurma to an array that contains it', () => {
        const transferenciaTurma: ITransferenciaTurma = sampleWithRequiredData;
        const transferenciaTurmaCollection: ITransferenciaTurma[] = [
          {
            ...transferenciaTurma,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTransferenciaTurmaToCollectionIfMissing(transferenciaTurmaCollection, transferenciaTurma);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TransferenciaTurma to an array that doesn't contain it", () => {
        const transferenciaTurma: ITransferenciaTurma = sampleWithRequiredData;
        const transferenciaTurmaCollection: ITransferenciaTurma[] = [sampleWithPartialData];
        expectedResult = service.addTransferenciaTurmaToCollectionIfMissing(transferenciaTurmaCollection, transferenciaTurma);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferenciaTurma);
      });

      it('should add only unique TransferenciaTurma to an array', () => {
        const transferenciaTurmaArray: ITransferenciaTurma[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const transferenciaTurmaCollection: ITransferenciaTurma[] = [sampleWithRequiredData];
        expectedResult = service.addTransferenciaTurmaToCollectionIfMissing(transferenciaTurmaCollection, ...transferenciaTurmaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transferenciaTurma: ITransferenciaTurma = sampleWithRequiredData;
        const transferenciaTurma2: ITransferenciaTurma = sampleWithPartialData;
        expectedResult = service.addTransferenciaTurmaToCollectionIfMissing([], transferenciaTurma, transferenciaTurma2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferenciaTurma);
        expect(expectedResult).toContain(transferenciaTurma2);
      });

      it('should accept null and undefined values', () => {
        const transferenciaTurma: ITransferenciaTurma = sampleWithRequiredData;
        expectedResult = service.addTransferenciaTurmaToCollectionIfMissing([], null, transferenciaTurma, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferenciaTurma);
      });

      it('should return initial array if no TransferenciaTurma is added', () => {
        const transferenciaTurmaCollection: ITransferenciaTurma[] = [sampleWithRequiredData];
        expectedResult = service.addTransferenciaTurmaToCollectionIfMissing(transferenciaTurmaCollection, undefined, null);
        expect(expectedResult).toEqual(transferenciaTurmaCollection);
      });
    });

    describe('compareTransferenciaTurma', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTransferenciaTurma(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTransferenciaTurma(entity1, entity2);
        const compareResult2 = service.compareTransferenciaTurma(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTransferenciaTurma(entity1, entity2);
        const compareResult2 = service.compareTransferenciaTurma(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTransferenciaTurma(entity1, entity2);
        const compareResult2 = service.compareTransferenciaTurma(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
