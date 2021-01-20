//package com.wzh.lab.demo;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//
//import org.opencv.core.*;
//import org.opencv.features2d.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.objdetect.CascadeClassifier;
//
//public class ExtractSIFT2 {
//    public static void main(String[] args) {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        Mat src = Imgcodecs.imread("D:/img/ocr4.jpg");
//        Mat dst = Imgcodecs.imread("D:/img/ocr5.png");
//        MatOfRect mr = getFace(dst);
//        Mat sub = dst.submat(mr.toArray()[0]);
//
//        Imgcodecs.imwrite("D:/img/Y4.jpg", FeatureSurfBruteforce(src.t(), sub));
//        Imgcodecs.imwrite("D:/img/Y5.jpg", FeatureSiftLannbased(src.t(), sub));
//        Imgcodecs.imwrite("D:/img/Y6.jpg", FeatureOrbLannbased(src.t(), sub));
//    }
//
//    public static Mat FeatureSurfBruteforce(Mat src, Mat dst) {
//        FeatureDetector fd = FeatureDetector.create(FeatureDetector.SURF);
//        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SURF);
//        // DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
//        DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);
//
//        MatOfKeyPoint mkp = new MatOfKeyPoint();
//        fd.detect(src, mkp);
//        Mat desc = new Mat();
//        de.compute(src, mkp, desc);
//        Features2d.drawKeypoints(src, mkp, src);
//
//        MatOfKeyPoint mkp2 = new MatOfKeyPoint();
//        fd.detect(dst, mkp2);
//        Mat desc2 = new Mat();
//        de.compute(dst, mkp2, desc2);
//        Features2d.drawKeypoints(dst, mkp2, dst);
//
//        // Matching features
//        MatOfDMatch Matches = new MatOfDMatch();
//        Matcher.match(desc, desc2, Matches);
//
//        double maxDist = Double.MIN_VALUE;
//        double minDist = Double.MAX_VALUE;
//
//        DMatch[] mats = Matches.toArray();
//        for (int i = 0; i < mats.length; i++) {
//            double dist = mats[i].distance;
//            if (dist < minDist) {
//                minDist = dist;
//            }
//            if (dist > maxDist) {
//                maxDist = dist;
//            }
//        }
//        System.out.println("Min Distance:" + minDist);
//        System.out.println("Max Distance:" + maxDist);
//        List<DMatch> goodMatch = new LinkedList<>();
//
//        for (int i = 0; i < mats.length; i++) {
//            double dist = mats[i].distance;
//            if (dist < 3 * minDist && dist < 0.2f) {
//                goodMatch.add(mats[i]);
//            }
//        }
//
//        Matches.fromList(goodMatch);
//        // Show result
//        Mat OutImage = new Mat();
//        Features2d.drawMatches(src, mkp, dst, mkp2, Matches, OutImage);
//
//        return OutImage;
//    }
//
//    public static Mat FeatureSiftLannbased(Mat src, Mat dst) {
//        FeatureDetector fd = FeatureDetector.create(FeatureDetector.SIFT);
//        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SIFT);
//        DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
//
//        MatOfKeyPoint mkp = new MatOfKeyPoint();
//        fd.detect(src, mkp);
//        Mat desc = new Mat();
//        de.compute(src, mkp, desc);
//        Features2d.drawKeypoints(src, mkp, src);
//
//        MatOfKeyPoint mkp2 = new MatOfKeyPoint();
//        fd.detect(dst, mkp2);
//        Mat desc2 = new Mat();
//        de.compute(dst, mkp2, desc2);
//        Features2d.drawKeypoints(dst, mkp2, dst);
//
//        // Matching features
//        MatOfDMatch Matches = new MatOfDMatch();
//        Matcher.match(desc, desc2, Matches);
//
//        List<DMatch> l = Matches.toList();
//        List<DMatch> goodMatch = new ArrayList<DMatch>();
//        for (int i = 0; i < l.size(); i++) {
//            DMatch dmatch = l.get(i);
//            if (Math.abs(dmatch.queryIdx - dmatch.trainIdx) < 10f) {
//                goodMatch.add(dmatch);
//            }
//
//        }
//
//        Matches.fromList(goodMatch);
//        // Show result
//        Mat OutImage = new Mat();
//        Features2d.drawMatches(src, mkp, dst, mkp2, Matches, OutImage);
//
//        return OutImage;
//    }
//
//    public static Mat FeatureOrbLannbased(Mat src, Mat dst) {
//        FeatureDetector fd = FeatureDetector.create(FeatureDetector.ORB);
//        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.ORB);
//        DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);
//
//        MatOfKeyPoint mkp = new MatOfKeyPoint();
//        fd.detect(src, mkp);
//        Mat desc = new Mat();
//        de.compute(src, mkp, desc);
//        Features2d.drawKeypoints(src, mkp, src);
//
//        MatOfKeyPoint mkp2 = new MatOfKeyPoint();
//        fd.detect(dst, mkp2);
//        Mat desc2 = new Mat();
//        de.compute(dst, mkp2, desc2);
//        Features2d.drawKeypoints(dst, mkp2, dst);
//
//        // Matching features
//
//        MatOfDMatch Matches = new MatOfDMatch();
//        Matcher.match(desc, desc2, Matches);
//
//        double maxDist = Double.MIN_VALUE;
//        double minDist = Double.MAX_VALUE;
//
//        DMatch[] mats = Matches.toArray();
//        for (int i = 0; i < mats.length; i++) {
//            double dist = mats[i].distance;
//            if (dist < minDist) {
//                minDist = dist;
//            }
//            if (dist > maxDist) {
//                maxDist = dist;
//            }
//        }
//        System.out.println("Min Distance:" + minDist);
//        System.out.println("Max Distance:" + maxDist);
//        List<DMatch> goodMatch = new LinkedList<>();
//
//        for (int i = 0; i < mats.length; i++) {
//            double dist = mats[i].distance;
//            if (dist < 3 * minDist && dist < 0.2f) {
//                goodMatch.add(mats[i]);
//            }
//        }
//
//        Matches.fromList(goodMatch);
//        // Show result
//        Mat OutImage = new Mat();
//        Features2d.drawMatches(src, mkp, dst, mkp2, Matches, OutImage);
//
//        // Highgui.imwrite("E:/work/qqq/Y4.jpg", OutImage);
//        return OutImage;
//    }
//
//    public static MatOfRect getFace(Mat src) {
//        Mat result = src.clone();
//        if (src.cols() > 1000 || src.rows() > 1000) {
//            Imgproc.resize(src, result, new Size(src.cols() / 3, src.rows() / 3));
//        }
//
//        CascadeClassifier faceDetector = new CascadeClassifier("./resource/haarcascade_frontalface_alt2.xml");
//        MatOfRect objDetections = new MatOfRect();
//        faceDetector.detectMultiScale(result, objDetections);
//
//        return objDetections;
//    }
//}
