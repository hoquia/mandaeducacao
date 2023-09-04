import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResumoImpostoFactura } from '../resumo-imposto-factura.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../resumo-imposto-factura.test-samples';

import { ResumoImpostoFacturaService } from './resumo-imposto-factura.service';

const requireRestSample: IResumoImpostoFactura = {
  ...sampleWithRequiredData,
};

describe('ResumoImpostoFactura Service', () => {
  let service: ResumoImpostoFacturaService;
  let httpMock: HttpTestingController;
  let expectedResult: IResumoImpostoFactura | IResumoImpostoFactura[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumoImpostoFacturaService);
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

    it('should create a ResumoImpostoFactura', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const resumoImpostoFactura = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(resumoImpostoFactura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumoImpostoFactura', () => {
      const resumoImpostoFactura = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(resumoImpostoFactura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResumoImpostoFactura', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumoImpostoFactura', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResumoImpostoFactura', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResumoImpostoFacturaToCollectionIfMissing', () => {
      it('should add a ResumoImpostoFactura to an empty array', () => {
        const resumoImpostoFactura: IResumoImpostoFactura = sampleWithRequiredData;
        expectedResult = service.addResumoImpostoFacturaToCollectionIfMissing([], resumoImpostoFactura);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumoImpostoFactura);
      });

      it('should not add a ResumoImpostoFactura to an array that contains it', () => {
        const resumoImpostoFactura: IResumoImpostoFactura = sampleWithRequiredData;
        const resumoImpostoFacturaCollection: IResumoImpostoFactura[] = [
          {
            ...resumoImpostoFactura,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResumoImpostoFacturaToCollectionIfMissing(resumoImpostoFacturaCollection, resumoImpostoFactura);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumoImpostoFactura to an array that doesn't contain it", () => {
        const resumoImpostoFactura: IResumoImpostoFactura = sampleWithRequiredData;
        const resumoImpostoFacturaCollection: IResumoImpostoFactura[] = [sampleWithPartialData];
        expectedResult = service.addResumoImpostoFacturaToCollectionIfMissing(resumoImpostoFacturaCollection, resumoImpostoFactura);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumoImpostoFactura);
      });

      it('should add only unique ResumoImpostoFactura to an array', () => {
        const resumoImpostoFacturaArray: IResumoImpostoFactura[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const resumoImpostoFacturaCollection: IResumoImpostoFactura[] = [sampleWithRequiredData];
        expectedResult = service.addResumoImpostoFacturaToCollectionIfMissing(resumoImpostoFacturaCollection, ...resumoImpostoFacturaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumoImpostoFactura: IResumoImpostoFactura = sampleWithRequiredData;
        const resumoImpostoFactura2: IResumoImpostoFactura = sampleWithPartialData;
        expectedResult = service.addResumoImpostoFacturaToCollectionIfMissing([], resumoImpostoFactura, resumoImpostoFactura2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumoImpostoFactura);
        expect(expectedResult).toContain(resumoImpostoFactura2);
      });

      it('should accept null and undefined values', () => {
        const resumoImpostoFactura: IResumoImpostoFactura = sampleWithRequiredData;
        expectedResult = service.addResumoImpostoFacturaToCollectionIfMissing([], null, resumoImpostoFactura, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumoImpostoFactura);
      });

      it('should return initial array if no ResumoImpostoFactura is added', () => {
        const resumoImpostoFacturaCollection: IResumoImpostoFactura[] = [sampleWithRequiredData];
        expectedResult = service.addResumoImpostoFacturaToCollectionIfMissing(resumoImpostoFacturaCollection, undefined, null);
        expect(expectedResult).toEqual(resumoImpostoFacturaCollection);
      });
    });

    describe('compareResumoImpostoFactura', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResumoImpostoFactura(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareResumoImpostoFactura(entity1, entity2);
        const compareResult2 = service.compareResumoImpostoFactura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareResumoImpostoFactura(entity1, entity2);
        const compareResult2 = service.compareResumoImpostoFactura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareResumoImpostoFactura(entity1, entity2);
        const compareResult2 = service.compareResumoImpostoFactura(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
