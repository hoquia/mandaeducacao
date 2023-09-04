import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResponsavelTurno } from '../responsavel-turno.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../responsavel-turno.test-samples';

import { ResponsavelTurnoService, RestResponsavelTurno } from './responsavel-turno.service';

const requireRestSample: RestResponsavelTurno = {
  ...sampleWithRequiredData,
  de: sampleWithRequiredData.de?.format(DATE_FORMAT),
  ate: sampleWithRequiredData.ate?.format(DATE_FORMAT),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('ResponsavelTurno Service', () => {
  let service: ResponsavelTurnoService;
  let httpMock: HttpTestingController;
  let expectedResult: IResponsavelTurno | IResponsavelTurno[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResponsavelTurnoService);
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

    it('should create a ResponsavelTurno', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const responsavelTurno = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(responsavelTurno).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResponsavelTurno', () => {
      const responsavelTurno = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(responsavelTurno).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResponsavelTurno', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResponsavelTurno', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResponsavelTurno', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResponsavelTurnoToCollectionIfMissing', () => {
      it('should add a ResponsavelTurno to an empty array', () => {
        const responsavelTurno: IResponsavelTurno = sampleWithRequiredData;
        expectedResult = service.addResponsavelTurnoToCollectionIfMissing([], responsavelTurno);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelTurno);
      });

      it('should not add a ResponsavelTurno to an array that contains it', () => {
        const responsavelTurno: IResponsavelTurno = sampleWithRequiredData;
        const responsavelTurnoCollection: IResponsavelTurno[] = [
          {
            ...responsavelTurno,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResponsavelTurnoToCollectionIfMissing(responsavelTurnoCollection, responsavelTurno);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResponsavelTurno to an array that doesn't contain it", () => {
        const responsavelTurno: IResponsavelTurno = sampleWithRequiredData;
        const responsavelTurnoCollection: IResponsavelTurno[] = [sampleWithPartialData];
        expectedResult = service.addResponsavelTurnoToCollectionIfMissing(responsavelTurnoCollection, responsavelTurno);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelTurno);
      });

      it('should add only unique ResponsavelTurno to an array', () => {
        const responsavelTurnoArray: IResponsavelTurno[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const responsavelTurnoCollection: IResponsavelTurno[] = [sampleWithRequiredData];
        expectedResult = service.addResponsavelTurnoToCollectionIfMissing(responsavelTurnoCollection, ...responsavelTurnoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const responsavelTurno: IResponsavelTurno = sampleWithRequiredData;
        const responsavelTurno2: IResponsavelTurno = sampleWithPartialData;
        expectedResult = service.addResponsavelTurnoToCollectionIfMissing([], responsavelTurno, responsavelTurno2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelTurno);
        expect(expectedResult).toContain(responsavelTurno2);
      });

      it('should accept null and undefined values', () => {
        const responsavelTurno: IResponsavelTurno = sampleWithRequiredData;
        expectedResult = service.addResponsavelTurnoToCollectionIfMissing([], null, responsavelTurno, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelTurno);
      });

      it('should return initial array if no ResponsavelTurno is added', () => {
        const responsavelTurnoCollection: IResponsavelTurno[] = [sampleWithRequiredData];
        expectedResult = service.addResponsavelTurnoToCollectionIfMissing(responsavelTurnoCollection, undefined, null);
        expect(expectedResult).toEqual(responsavelTurnoCollection);
      });
    });

    describe('compareResponsavelTurno', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResponsavelTurno(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareResponsavelTurno(entity1, entity2);
        const compareResult2 = service.compareResponsavelTurno(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareResponsavelTurno(entity1, entity2);
        const compareResult2 = service.compareResponsavelTurno(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareResponsavelTurno(entity1, entity2);
        const compareResult2 = service.compareResponsavelTurno(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
