import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResumoAcademico } from '../resumo-academico.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../resumo-academico.test-samples';

import { ResumoAcademicoService } from './resumo-academico.service';

const requireRestSample: IResumoAcademico = {
  ...sampleWithRequiredData,
};

describe('ResumoAcademico Service', () => {
  let service: ResumoAcademicoService;
  let httpMock: HttpTestingController;
  let expectedResult: IResumoAcademico | IResumoAcademico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResumoAcademicoService);
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

    it('should create a ResumoAcademico', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const resumoAcademico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(resumoAcademico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResumoAcademico', () => {
      const resumoAcademico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(resumoAcademico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResumoAcademico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResumoAcademico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResumoAcademico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResumoAcademicoToCollectionIfMissing', () => {
      it('should add a ResumoAcademico to an empty array', () => {
        const resumoAcademico: IResumoAcademico = sampleWithRequiredData;
        expectedResult = service.addResumoAcademicoToCollectionIfMissing([], resumoAcademico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumoAcademico);
      });

      it('should not add a ResumoAcademico to an array that contains it', () => {
        const resumoAcademico: IResumoAcademico = sampleWithRequiredData;
        const resumoAcademicoCollection: IResumoAcademico[] = [
          {
            ...resumoAcademico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResumoAcademicoToCollectionIfMissing(resumoAcademicoCollection, resumoAcademico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResumoAcademico to an array that doesn't contain it", () => {
        const resumoAcademico: IResumoAcademico = sampleWithRequiredData;
        const resumoAcademicoCollection: IResumoAcademico[] = [sampleWithPartialData];
        expectedResult = service.addResumoAcademicoToCollectionIfMissing(resumoAcademicoCollection, resumoAcademico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumoAcademico);
      });

      it('should add only unique ResumoAcademico to an array', () => {
        const resumoAcademicoArray: IResumoAcademico[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const resumoAcademicoCollection: IResumoAcademico[] = [sampleWithRequiredData];
        expectedResult = service.addResumoAcademicoToCollectionIfMissing(resumoAcademicoCollection, ...resumoAcademicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resumoAcademico: IResumoAcademico = sampleWithRequiredData;
        const resumoAcademico2: IResumoAcademico = sampleWithPartialData;
        expectedResult = service.addResumoAcademicoToCollectionIfMissing([], resumoAcademico, resumoAcademico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resumoAcademico);
        expect(expectedResult).toContain(resumoAcademico2);
      });

      it('should accept null and undefined values', () => {
        const resumoAcademico: IResumoAcademico = sampleWithRequiredData;
        expectedResult = service.addResumoAcademicoToCollectionIfMissing([], null, resumoAcademico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resumoAcademico);
      });

      it('should return initial array if no ResumoAcademico is added', () => {
        const resumoAcademicoCollection: IResumoAcademico[] = [sampleWithRequiredData];
        expectedResult = service.addResumoAcademicoToCollectionIfMissing(resumoAcademicoCollection, undefined, null);
        expect(expectedResult).toEqual(resumoAcademicoCollection);
      });
    });

    describe('compareResumoAcademico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResumoAcademico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareResumoAcademico(entity1, entity2);
        const compareResult2 = service.compareResumoAcademico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareResumoAcademico(entity1, entity2);
        const compareResult2 = service.compareResumoAcademico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareResumoAcademico(entity1, entity2);
        const compareResult2 = service.compareResumoAcademico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
