import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlanoDesconto } from '../plano-desconto.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plano-desconto.test-samples';

import { PlanoDescontoService } from './plano-desconto.service';

const requireRestSample: IPlanoDesconto = {
  ...sampleWithRequiredData,
};

describe('PlanoDesconto Service', () => {
  let service: PlanoDescontoService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlanoDesconto | IPlanoDesconto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlanoDescontoService);
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

    it('should create a PlanoDesconto', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const planoDesconto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(planoDesconto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlanoDesconto', () => {
      const planoDesconto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(planoDesconto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlanoDesconto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlanoDesconto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlanoDesconto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlanoDescontoToCollectionIfMissing', () => {
      it('should add a PlanoDesconto to an empty array', () => {
        const planoDesconto: IPlanoDesconto = sampleWithRequiredData;
        expectedResult = service.addPlanoDescontoToCollectionIfMissing([], planoDesconto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoDesconto);
      });

      it('should not add a PlanoDesconto to an array that contains it', () => {
        const planoDesconto: IPlanoDesconto = sampleWithRequiredData;
        const planoDescontoCollection: IPlanoDesconto[] = [
          {
            ...planoDesconto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlanoDescontoToCollectionIfMissing(planoDescontoCollection, planoDesconto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlanoDesconto to an array that doesn't contain it", () => {
        const planoDesconto: IPlanoDesconto = sampleWithRequiredData;
        const planoDescontoCollection: IPlanoDesconto[] = [sampleWithPartialData];
        expectedResult = service.addPlanoDescontoToCollectionIfMissing(planoDescontoCollection, planoDesconto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoDesconto);
      });

      it('should add only unique PlanoDesconto to an array', () => {
        const planoDescontoArray: IPlanoDesconto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const planoDescontoCollection: IPlanoDesconto[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoDescontoToCollectionIfMissing(planoDescontoCollection, ...planoDescontoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const planoDesconto: IPlanoDesconto = sampleWithRequiredData;
        const planoDesconto2: IPlanoDesconto = sampleWithPartialData;
        expectedResult = service.addPlanoDescontoToCollectionIfMissing([], planoDesconto, planoDesconto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoDesconto);
        expect(expectedResult).toContain(planoDesconto2);
      });

      it('should accept null and undefined values', () => {
        const planoDesconto: IPlanoDesconto = sampleWithRequiredData;
        expectedResult = service.addPlanoDescontoToCollectionIfMissing([], null, planoDesconto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoDesconto);
      });

      it('should return initial array if no PlanoDesconto is added', () => {
        const planoDescontoCollection: IPlanoDesconto[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoDescontoToCollectionIfMissing(planoDescontoCollection, undefined, null);
        expect(expectedResult).toEqual(planoDescontoCollection);
      });
    });

    describe('comparePlanoDesconto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlanoDesconto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlanoDesconto(entity1, entity2);
        const compareResult2 = service.comparePlanoDesconto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlanoDesconto(entity1, entity2);
        const compareResult2 = service.comparePlanoDesconto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlanoDesconto(entity1, entity2);
        const compareResult2 = service.comparePlanoDesconto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
